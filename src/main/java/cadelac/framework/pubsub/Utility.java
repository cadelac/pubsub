package cadelac.framework.pubsub;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.pubsub.message.ExceptionMsg;
import cadelac.framework.pubsub.message.PacketMsg;
import de.jackwhite20.japs.client.pub.Publisher;

public class Utility {
	
	public static void publishExceptionResponse(
			final String exceptionText_
			, final PacketMsg packet_
			, final ChannelId channelId_) 
					throws Exception {
		logger.warn(exceptionText_);
		ExceptionMsg.create(exceptionText_)
				.wrap(packet_)
				.publish(channelId_);
	}

	/**
	 * publish to monitor channel
	 * @param jsonEncoded_
	 * @throws Exception
	 */
	public static void pubMon(
			final String jsonEncoded_) 
					throws Exception {
		// non-directed to subscriber
		final Publisher publisher = BusChannel.getPublisher();
		if(publisher != null)
			if(publisher.connected())
				publisher.publish(BusChannel.MONITOR.getId(), jsonEncoded_);
			else
				logger.error("Cannot publish, publisher is null. Message:" + jsonEncoded_);
		else
			logger.error("Cannot publish, publisher is not connected. Message:" + jsonEncoded_);
	}

	/**
	 * publish to specified channel
	 * @param jsonEncoded_
	 * @throws Exception
	 */
	public static void pubChan(
			final String subscriberName_
			, final String event_
			, final String channelName_
			, final String jsonEncoded_) 
					throws Exception {
		final Publisher publisher = BusChannel.getPublisher();

		final String sub = 
				(subscriberName_!=null && !subscriberName_.isEmpty())
				? subscriberName_ : "unspecified-subscriber";
		
		final String event = 
				(event_!=null && !event_.isEmpty())
				? event_ : "unspecified-event";
		
		logger.info(String.format(
				"app %s >>> publish %s to %s on channel %s: %s"
				, Framework.getApplication().getId()
				, event
				, sub
				, channelName_
				, jsonEncoded_));
		
		if(publisher != null) {
			if(publisher.connected()) {
				if (subscriberName_!=null && !subscriberName_.isEmpty()) {
					publisher.publish(channelName_, subscriberName_, jsonEncoded_);
				}
				else {
					publisher.publish(channelName_, jsonEncoded_);
				}
			} else {
				logger.error("Cannot publish, publisher is null. Message:" + jsonEncoded_);
			}
		} else {
			logger.error("Cannot publish, publisher is not connected. Message:" + jsonEncoded_);
		}
	}
	
	public static void pubChan(
			final String subscriberName_
			, final String event_
			, final String channelName_
			, final PacketMsg packet_) 
					throws Exception {
		pubChan(subscriberName_, event_, channelName_, JsonFormat.encode(packet_));
	}
	
	public static void surpub(
			final String subscriber_
			, final String event_
			, final ChannelId channelId_
			, final String jsonEncoded) 
					throws Exception {
		pubChan(subscriber_, event_, channelId_.getId(), jsonEncoded);
		pubMon(jsonEncoded);
	}
	
	public static void surpub(
			final String subscriber_
			, final ChannelId channelId_
			, final String jsonEncoded) 
					throws Exception {
		surpub(subscriber_, null, channelId_, jsonEncoded);
	}
	
	public static void surpub(
			final ChannelId channelId_
			, final String jsonEncoded) 
					throws Exception {
		surpub(null, null, channelId_, jsonEncoded);
	}
	
	public static void surpub(
			final String subscriber_
			, final PacketMsg packet_
			, final ChannelId channelId_) 
					throws Exception {
		surpub(subscriber_, packet_.getEvent(), channelId_, JsonFormat.encode(packet_));
		packet_.audit(channelId_.getId());
	}
	
	
	public static void surpub(
			final PacketMsg packet_
			, final ChannelId channelId_) 
					throws Exception {
		surpub(null, packet_, channelId_);
	}
	
	
	static final Logger logger = LogManager.getLogger(Utility.class);
}

package cadelac.framework.pubsub.message;

import org.apache.log4j.Logger;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.Message;
import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.blade.core.object.ObjectPopulator;
import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.ChannelId;
import cadelac.framework.pubsub.Utility;
import cadelac.framework.pubsub.message.base.HasEvent;
import cadelac.framework.pubsub.message.base.HasGatewayAddress;
import cadelac.framework.pubsub.message.base.HasOrigin;
import cadelac.framework.pubsub.message.base.HasPayload;
import cadelac.framework.pubsub.message.base.HasReference;
import cadelac.framework.pubsub.message.base.HasSequenceId;
import cadelac.framework.pubsub.message.base.HasTimestamp;
import cadelac.framework.pubsub.message.base.HasToken;

public interface PacketMsg 
		extends Message
		, HasSequenceId
		, HasTimestamp 
		, HasEvent
		, HasOrigin
		, HasPayload
		
		// authentication token
		// mandatory when authentication is enforced
		, HasToken
		
		// optional; sequence id of a related message
		, HasReference
				
		// reserved; used for routing through a gateway
		, HasGatewayAddress {

	default PacketMsg publish(final ChannelId channel_) 
			throws Exception {
		
		final String jsonEncoded = JsonFormat.encode(this);
		final String channelName = channel_.getId();
		final String formattedText = String.format(
				"app %s >>> publish %s on channel %s: %s"
				, Framework.getApplication().getId()
				, getEvent()
				, channelName
				, jsonEncoded);
		
		Utility.monitor(formattedText);
		logger.info(formattedText);
		
		BusChannel.getPublisher().publish(
				channelName
				, jsonEncoded);
		
		return this;
	}
	default PacketMsg publish(final String subscriberName_, final ChannelId channel_) 
			throws Exception {
		
		final String jsonEncoded = JsonFormat.encode(this);
		final String channelName = channel_.getId();
		final String formattedText = String.format(
				"app %s >>> publish %s to %s on channel %s: %s"
				, Framework.getApplication().getId()
				, getEvent()
				, subscriberName_
				, channelName
				, jsonEncoded);
		
		Utility.monitor(formattedText);
		logger.info(formattedText);
		
		BusChannel.getPublisher().publish(
				channelName
				, subscriberName_
				, jsonEncoded);
		
		return this;
	}
	
	// surveilled publish
	default PacketMsg surpub(final String subscriberName_, final ChannelId channel_) 
			throws Exception {
		
		final String jsonEncoded = JsonFormat.encode(this);
		final String channelName = channel_.getId();
		final String formattedText = String.format(
				"app %s >>> surveilled publish %s to %s on channel %s: %s"
				, Framework.getApplication().getId()
				, getEvent()
				, subscriberName_
				, channelName
				, jsonEncoded);
		logger.info(formattedText);
		
		BusChannel.getPublisher().publish(
				channelName
				, subscriberName_
				, jsonEncoded);
		Utility.surpub(jsonEncoded);
		
		return this;
	}
	// surveilled publish
	default PacketMsg surpub(final ChannelId channel_) 
			throws Exception {
		
		final String jsonEncoded = JsonFormat.encode(this);
		final String channelName = channel_.getId();
		final String formattedText = String.format(
				"app %s >>> surveilled publish %s on channel %s: %s"
				, Framework.getApplication().getId()
				, getEvent()
				, channelName
				, jsonEncoded);
		logger.info(formattedText);
		
		BusChannel.getPublisher().publish(
				channelName
				, jsonEncoded);
		Utility.surpub(jsonEncoded);
		
		return this;
	}
	
	
	
	static PacketMsg createEventMsg(
			final PayloadMsg payload_
			, final String event_) 
					throws Exception {
		return createPacketMsg(p -> {
			p.setPayload(payload_);
			p.setEvent(event_);
		});	
	}
	
	static PacketMsg createPacketMsg() 
			throws Exception {
		return createPacketMsg(null);	
	}
	
	static PacketMsg createPacketMsg(
			final ObjectPopulator<PacketMsg> objectPopulator_) 
					throws Exception {

		final PacketMsg packet = 
				Framework.getObjectFactory().fabricate(
						PacketMsg.class
						, objectPopulator_);
		packet.setOrigin(Framework.getApplication().getId());
		packet.populateSequenceId();
		packet.populateTimestamp();
		return packet;
	}
	
	
	static final Logger logger = Logger.getLogger(PacketMsg.class);
}

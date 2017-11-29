package cadelac.framework.pubsub.message;

import org.apache.log4j.Logger;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.Utilities;
import cadelac.framework.blade.core.message.Message;
import cadelac.framework.blade.core.message.json.JsonEncoder;
import cadelac.framework.blade.core.object.ObjectPopulator;
import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.ChannelId;
import cadelac.framework.pubsub.message.base.HasTimestamp;

public interface PacketMsg extends Message, HasTimestamp {

	String PAYLOAD = "Payload";
	
	long getSequenceId();
	void setSequenceId(long sequenceId);

	String getChannel();
	void setChannel(String channel);
	
	String getEvent();
	void setEvent(String event);
	
	String getType();
	void setType(String type);
	
	String getOrigin();
	void setOrigin(String origin);
	
	PayloadMsg getPayload();
	void setPayload(PayloadMsg payload);
	

	
	default PacketMsg publishOn(final ChannelId channel_) 
			throws Exception {
		
		final String jsonEncoded = JsonEncoder.encode(this);
		final String channelName = channel_.getChannelName();
		
		logger.info(String.format(
				"\n\t app %s >>> publish %s on channel %s:\n%s\n"
				, Framework.getApplication().getId()
				, getEvent()
				, channelName
				, jsonEncoded));
		
		BusChannel.getPublisher().publish(
				channelName
				, jsonEncoded);
		
		return this;
	}
	default PacketMsg publishOn(final String subscriberName_, final ChannelId channel_) 
			throws Exception {
		
		final String jsonEncoded = JsonEncoder.encode(this);
		final String channelName = channel_.getChannelName();
		
		logger.info(String.format(
				"\n\t app %s >>> publish %s to %s on channel %s:\n%s\n"
				, Framework.getApplication().getId()
				, getEvent()
				, subscriberName_
				, channelName
				, jsonEncoded));
		
		BusChannel.getPublisher().publish(
				channelName
				, subscriberName_
				, jsonEncoded);
		
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

		final PacketMsg packet = Framework.getObjectFactory().fabricate(PacketMsg.class, objectPopulator_);
		packet.setOrigin(Framework.getApplication().getId());
		packet.setSequenceId(Framework.getMonitor().getNextSequenceId());
		packet.setTimestamp(Utilities.getTimestamp());
		return packet;
	}
	
	
	static final Logger logger = Logger.getLogger(PacketMsg.class);
}

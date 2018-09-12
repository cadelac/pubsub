package cadelac.framework.pubsub.message;

import static cadelac.framework.blade.Framework.$store;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.dispatch.MessageBlock;
import cadelac.framework.blade.core.message.Message;
import cadelac.framework.blade.core.object.ObjectPopulator;
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
import de.jackwhite20.japs.client.cache.Cacheable;

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
		, HasGatewayAddress 
		
		, Cacheable { // for pubsub cache

	
	
	String STORE_ENTRY_PACKET_TRAIL = "storeEntryPacketTrail";

	default PayloadMsg checkedGetPayload(
			ChannelId channelId_) 
					throws Exception {
		final PayloadMsg payload = getPayload();
		if (payload == null) {
			// publish error message
			Utility.publishExceptionResponse(
					"Error: malformed packet; missing payload"
					, this
					, channelId_);
		}
		return payload;
	}

	default PacketMsg publish(
			final String subscriberName_
			, final ChannelId channelId_) 
					throws Exception {
		Utility.pubChan(
				subscriberName_
				, getEvent()
				, channelId_.getId()
				, this);
		return this;
	}
	
	
	default PacketMsg publish(
			final ChannelId channelId_) 
					throws Exception {
		publish(null, channelId_);
		return this;
	}
	
	

	default PacketMsg surpub(
			final String subscriberName_
			, final ChannelId channelId_) 
					throws Exception {
		Utility.surpub(
				subscriberName_
				, this
				, channelId_);
		return this;
	}

	
	default PacketMsg surpub(
			final ChannelId channelId_)
					throws Exception {
		surpub(null, channelId_);
		return this;
	}
	
	
	default PacketMsg audit(
			final String channel_) 
					throws Exception {
		
		final MessageBlock<PacketMsg> messageBlock = $store.getValue(
		        		STORE_ENTRY_PACKET_TRAIL);
		if (messageBlock!=null)
			messageBlock.block(channel_, this);
		return this;
	}
	
	default PacketMsg audit(
			final boolean isSaved_
			, final String channel_) 
					throws Exception {
		if (isSaved_) {
			this.audit(channel_);
		}
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
}

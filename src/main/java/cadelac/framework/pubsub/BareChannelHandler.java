package cadelac.framework.pubsub;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.pubsub.message.MsgDecoder;
import cadelac.framework.pubsub.message.PacketMsg;

public abstract class BareChannelHandler extends PubsubChannelHandler {
	
	public static final String EVENT_KEY = "Event";
	
	protected void process(
			final JSONObject jsonObject
			, final String eventString_) 
					throws Exception {
		
		jsonObject.put(EVENT_KEY, eventString_);
		// decode into packet
		final PacketMsg packet = decode(jsonObject);
		monitor(packet);
		MsgDecoder.processMessage(packet);
	}

	protected PacketMsg decode(
			final JSONObject jsonObject_) 
					throws Exception {
		return MsgDecoder.decode(jsonObject_);
	}
	
	protected abstract String getChannelName();
	
	protected void monitor(final PacketMsg packet) throws Exception {
		final String jsonEncoded = JsonFormat.encode(packet);
		final String formattedText = String.format(
				"\n\t app %s <<< received %s on channel %s:\n%s\n"
				, Framework.getApplication().getId()
				, packet.getEvent()
				, getChannelName()
				, jsonEncoded);
		surveil(formattedText);
		logger.info(formattedText);
	}
	
	protected void surveil(
			final String formattedText) 
					throws Exception {
		// this is a low-level channel handler
		// it must not cause a monitor message to be sent to MonitorApp
		// it will start an infinite sequence of MonitorMsg
	}
	
	static final Logger logger = Logger.getLogger(BareChannelHandler.class);
}

package cadelac.framework.pubsub;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.pubsub.message.MsgDecoder;
import cadelac.framework.pubsub.message.PacketMsg;
import cadelac.framework.pubsub.message.monitor.MonitorMsg;

public abstract class BareChannelHandler extends PubsubChannelHandler {
	
	public static final String EVENT_KEY = "Event";
	
	protected void process(
			final JSONObject jsonObject
			, final String eventString_) 
					throws Exception {
		
		jsonObject.put(EVENT_KEY, eventString_);
		// decode into packet
		final PacketMsg packet = decode(jsonObject);
		final String jsonEncoded = JsonFormat.encode(packet);
		final String formattedText = String.format(
				"app %s <<< received %s on channel %s:\n%s\n"
				, Framework.getApplication().getId()
				, packet.getEvent()
				, getChannelName()
				, jsonEncoded);
		logger.info(formattedText);
		Utility.pubMon(
				JsonFormat.encode(
						MonitorMsg.create(formattedText).wrap()));
		MsgDecoder.processMessage(packet);
	}

	protected PacketMsg decode(
			final JSONObject jsonObject_) 
					throws Exception {
		return MsgDecoder.decode(jsonObject_);
	}
	
	protected abstract String getChannelName();

	
	static final Logger logger = LogManager.getLogger(BareChannelHandler.class);
}

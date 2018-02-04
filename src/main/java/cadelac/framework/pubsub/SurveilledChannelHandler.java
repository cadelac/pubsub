package cadelac.framework.pubsub;

/*
import org.apache.log4j.Logger;
import org.json.JSONObject;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.pubsub.message.MsgDecoder;
import cadelac.framework.pubsub.message.PacketMsg;
*/

public abstract class SurveilledChannelHandler extends BareChannelHandler {
	/*
	public static final String EVENT_KEY = "Event";
	
	protected void process(
			final JSONObject jsonObject
			, final String eventString_) 
					throws Exception {
		
		jsonObject.put(EVENT_KEY, eventString_);
		
		final PacketMsg packet = MsgDecoder.decode(jsonObject);
		final String jsonEncoded = JsonFormat.encode(packet);
		final String formattedText = String.format(
				"\n\t app %s <<< received %s on channel %s:\n%s\n"
				, Framework.getApplication().getId()
				, packet.getEvent()
				, getChannelName()
				, jsonEncoded);
		surveil(formattedText);
		logger.info(formattedText);
    	MsgDecoder.processMessage(packet);
	}

	
	protected abstract String getChannelName();
	static final Logger logger = Logger.getLogger(ChannelHandler.class);
	*/
	
	protected void surveil(
			final String formattedText) 
					throws Exception {
		Utility.monitor(formattedText);
	}
}

package cadelac.framework.pubsub.prog.monitor.handler;

import org.json.JSONObject;

import cadelac.framework.pubsub.BareChannelHandler;
import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.SurveilledChannelHandler;

import de.jackwhite20.japs.client.sub.impl.handler.annotation.Channel;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Key;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Value;

@Channel(BusChannel.SCRIPT_CHANNEL_STRING)
public class ScriptChannelHandler extends BareChannelHandler {

//	@Key(EVENT_KEY)
//	@Value(QuoteMarketDataEvent.EVENT)
//	public void onQuoteMarketDataEvent(JSONObject jsonObject) 
//			throws Exception {
//		process(jsonObject, QuoteMarketDataEvent.EVENT);
//	}

	
	@Override
	protected String getChannelName() {
		return BusChannel.SCRIPT_CHANNEL_STRING;
	}

}

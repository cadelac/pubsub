package cadelac.framework.pubsub.prog.monitor.handler;

import org.json.JSONObject;

import cadelac.framework.pubsub.BareChannelHandler;
import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.message.monitor.MonitorMsg;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Channel;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Key;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Value;

@Channel(BusChannel.MONITOR_CHANNEL_STRING)
public class MonitorChannelHandler extends BareChannelHandler {

    @Key(EVENT_KEY)
    @Value(MonitorMsg.EVENT)
    public void onMonitorMsg(JSONObject jsonObject) 
    			throws Exception {
    		process(jsonObject, MonitorMsg.EVENT);
    }

    
	@Override
	protected String getChannelName() {
		return BusChannel.MONITOR_CHANNEL_STRING;
	}    
}

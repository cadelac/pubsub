package cadelac.framework.pubsub.prog.monitor.handler;

import org.json.JSONObject;

import cadelac.framework.pubsub.BareChannelHandler;
import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.message.system.LifecycleMsg;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Channel;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Key;
import de.jackwhite20.japs.client.sub.impl.handler.annotation.Value;

@Channel(BusChannel.SYSTEM_CHANNEL_STRING)
public class SystemChannelHandler extends BareChannelHandler {

	@Key(EVENT_KEY)
	@Value(LifecycleMsg.EVENT)
	public void onLifecycleMsg(JSONObject jsonObject) 
			throws Exception {
		process(jsonObject, LifecycleMsg.EVENT);
	}

	@Override
	protected String getChannelName() {
		return BusChannel.SYSTEM_CHANNEL_STRING;
	}
}

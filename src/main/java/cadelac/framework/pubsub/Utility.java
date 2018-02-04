package cadelac.framework.pubsub;

import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.pubsub.message.monitor.MonitorMsg;

public class Utility {

	public static void monitor(final String text_) 
			throws Exception {
		
		// we cannot use convenience method PacketMsg.publish() 
		// because it will trigger recursion and blow up the heap
		// instead, we use publish directly using publisher
		
		BusChannel.getPublisher().publish(
				BusChannel.MONITOR.getId()
				, JsonFormat.encode(
						MonitorMsg
						.create(text_)
						.wrap()));

		
	}

}

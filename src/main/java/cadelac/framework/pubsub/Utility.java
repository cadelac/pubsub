package cadelac.framework.pubsub;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.pubsub.message.PacketMsg;
import cadelac.framework.pubsub.message.monitor.MonitorMsg;

public class Utility {

	public static void monitor(final String text_) 
			throws Exception {
		
		// we cannot use convenience method PacketMsg.publish() 
		// because it will trigger recursion and blow up the heap
		// instead, we use publish directly using publisher
		
		/*
		we need to think more about this. monitor() is called in 
		1) PacketMsg.publish() to send a text monitor message, and
		2) SurveilledChannelHandler when receiving a message.
		maybe monitor() should be called explicitly...
		
				BusChannel.getPublisher().publish(
				BusChannel.MONITOR.getId()
				, JsonFormat.encode(
						MonitorMsg
						.create(text_)
						.wrap()));
		 */

	}

	/**
	 * surveilled publish
	 * @param jsonEncoded_
	 * @throws Exception
	 */
	public static void surpub(final String jsonEncoded_) 
			throws Exception {
		// non-directed to subscriber
		BusChannel.getPublisher().publish(
				BusChannel.MONITOR.getId()
				, jsonEncoded_);
	}

	
	public static void surpub(
			final String subscriber_
			, final ChannelId channel_
			, final String jsonEncoded) 
					throws Exception {

		final String channelName = channel_.getId();
		
		BusChannel.getPublisher().publish(
				channelName
				, subscriber_
				, jsonEncoded);
		Utility.surpub(jsonEncoded);
	}
	
	public static void surpub(
			final ChannelId channel_
			, final String jsonEncoded) 
					throws Exception {

		final String channelName = channel_.getId();
		
		BusChannel.getPublisher().publish(
				channelName
				, jsonEncoded);
		Utility.surpub(jsonEncoded);
	}
}

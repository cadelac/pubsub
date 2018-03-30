package cadelac.framework.pubsub;

import de.jackwhite20.japs.client.pub.Publisher;
import de.jackwhite20.japs.client.sub.Subscriber;

public class BusChannel {

	public static <H extends PubsubChannelHandler> void subscribe(
			final Class<H> channelHandler_) {
		BusChannel.getSubscriber().subscribeMulti(channelHandler_);
	}

	
	public static Subscriber getSubscriber() {
		return _subscriber;
	}
	public static void setSubscriber(Subscriber subscriber_) {
		_subscriber = subscriber_;
	}
	

	public static Publisher getPublisher() {
		return _publisher;
	}
	public static void setPublisher(Publisher publisher_) {
		_publisher = publisher_;
	}
	
	
	private static Subscriber _subscriber;
	private static Publisher _publisher;
	
	public static final String SYSTEM_CHANNEL_STRING		= "systemChannel";
	public static final String MONITOR_CHANNEL_STRING		= "monitorChannel";
	public static final String SCRIPT_CHANNEL_STRING		= "scriptChannel";
	
	public static final ChannelId SYSTEM	= new ChannelId(SYSTEM_CHANNEL_STRING);
	public static final ChannelId MONITOR	= new ChannelId(MONITOR_CHANNEL_STRING);
	public static final ChannelId SCRIPT	= new ChannelId(SCRIPT_CHANNEL_STRING);

}

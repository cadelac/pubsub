package cadelac.framework.pubsub;

import java.util.function.Consumer;

import cadelac.framework.pubsub.message.MsgDecoder;
import cadelac.framework.pubsub.message.PacketMsg;
import cadelac.framework.pubsub.message.PayloadMsg;
import cadelac.framework.pubsub.message.system.Meter;
import de.jackwhite20.japs.client.cache.PubSubCache;
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
	
	
	public static PubSubCache getPubSubCache() {
		return _cache;
	}
	public static void setPubSubCache(PubSubCache cache_) {
		_cache = cache_;
	}
	
	public static <P extends PayloadMsg> void getCache(
			final String key_
			, final Class<P> clazz_
			, Consumer<PacketMsg> consumer_) {
		
		PubSubCache cache = getPubSubCache();
	
		cache.get(key_, jsonObject -> {
			try {
				PacketMsg _packet = 
						MsgDecoder.directDecodePacket(
								jsonObject
								, Meter.class);
				consumer_.accept(_packet);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void putCache(
			String key_
			, PacketMsg packet_) {
		BusChannel.getPubSubCache().putObject(key_, packet_);
	}
	
	
	
	private static Subscriber _subscriber;
	private static Publisher _publisher;
	private static PubSubCache _cache;
	
	public static final String SYSTEM_CHANNEL_STRING		= "systemChannel";
	public static final String MONITOR_CHANNEL_STRING		= "monitorChannel";
	public static final String SCRIPT_CHANNEL_STRING		= "scriptChannel";
	
	public static final ChannelId SYSTEM	= new ChannelId(SYSTEM_CHANNEL_STRING);
	public static final ChannelId MONITOR	= new ChannelId(MONITOR_CHANNEL_STRING);
	public static final ChannelId SCRIPT	= new ChannelId(SCRIPT_CHANNEL_STRING);

}

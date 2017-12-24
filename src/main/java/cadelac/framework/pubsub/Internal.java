package cadelac.framework.pubsub;

import cadelac.framework.pubsub.message.PacketMsg;
import cadelac.framework.pubsub.message.PayloadMsg;

public class Internal {
	public Internal(PayloadMsg event_, ChannelId channelId_) { 
		_event = event_;
		_channelId = channelId_;
	}
	public PacketMsg publish() 
			throws Exception {
		return _event.wrap().publish(_channelId);
	}
	public PacketMsg publish(final String subscriberName_) 
			throws Exception {
		return _event.wrap().publish(subscriberName_, _channelId);
	}
	final PayloadMsg _event;
	final ChannelId _channelId;
}

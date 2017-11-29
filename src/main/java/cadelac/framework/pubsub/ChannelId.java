package cadelac.framework.pubsub;

public class ChannelId {

	public ChannelId(final String channelName_) {
		_channelName = channelName_;
	}
	
	public String getChannelName() {
		return _channelName;
	}
	
	private final String _channelName;
}

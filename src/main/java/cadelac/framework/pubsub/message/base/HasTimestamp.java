package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.core.message.Message;

public interface HasTimestamp extends Message {

	long getTimestamp();
	void setTimestamp(long timestamp);
}

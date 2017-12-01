package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.core.Utilities;

public interface HasTimestamp {

	long getTimestamp();
	void setTimestamp(long timestamp);
	
	default void populateTimestamp() {
		setTimestamp(Utilities.getTimestamp());
	}
}

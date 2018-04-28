package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.Utilities;
import cadelac.framework.blade.core.message.Message;

public interface HasTimestamp extends Message {

	long getTimestamp();
	void setTimestamp(long timestamp);
	
	default void populateTimestamp() {
		setTimestamp(Utilities.getTimestamp());
	}
	
	static HasTimestamp create(long timestamp_) 
			throws Exception {
		return Framework.getObjectFactory().fabricate(
				HasTimestamp.class
				, tm -> {
					tm.setTimestamp(timestamp_);
				});
	}
	
	static HasTimestamp create() 
			throws Exception {
		return create(0L);
	}
}

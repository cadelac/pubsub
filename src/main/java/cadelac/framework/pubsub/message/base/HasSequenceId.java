package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.Framework;

public interface HasSequenceId {
	
	long getSequenceId();
	void setSequenceId(long sequenceId);
	
	default void populateSequenceId() {
		this.setSequenceId(Framework.getMonitor().getNextSequenceId());
	}
}

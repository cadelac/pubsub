package cadelac.framework.pubsub.message.system;

import cadelac.framework.blade.Framework;
import cadelac.framework.pubsub.LifecycleEvent;
import cadelac.framework.pubsub.message.AdminMsg;
import cadelac.framework.pubsub.message.base.HasTimestamp;

public interface Meter 
		extends AdminMsg {
	
	String EVENT = "Meter";
	
	HasTimestamp getStartUpTime();
	void setStartUpTime(HasTimestamp startTime);

	HasTimestamp getHeartbeatTime();
	void setHeartbeatTime(HasTimestamp heartbeatTime);
	
	HasTimestamp getPongTime();
	void setPongTime(HasTimestamp pongTime);
	
	String getLifecycleEvent();
	void setLifecycleEvent(String lifecycleEvent);
	
	
	static Meter create() throws Exception {
		return Framework.getObjectFactory().fabricate(
				Meter.class
				, m -> {
					m.setStartUpTime(HasTimestamp.create());
					m.setHeartbeatTime(HasTimestamp.create());
					m.setPongTime(HasTimestamp.create());
					m.setLifecycleEvent(LifecycleEvent.DOWN.toString());
				});
	}
}

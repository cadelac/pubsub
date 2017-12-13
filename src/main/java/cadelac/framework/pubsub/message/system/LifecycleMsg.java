package cadelac.framework.pubsub.message.system;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.object.ObjectPopulator;
import cadelac.framework.pubsub.LifecycleEvent;
import cadelac.framework.pubsub.message.AdminDirective;
import cadelac.framework.pubsub.message.base.HasOrigin;

public interface LifecycleMsg 
		extends AdminDirective
		, HasOrigin {

	String EVENT = "LifecycleMsg";
	
	String getLifecycleEvent();
	void setLifecycleEvent(String lifecyleEvent);

	
	/**
	 * translated (from string to enum) version of 'get'
	 * @return LifecycleEvent
	 */
	default LifecycleEvent xgetLifecycleEvent() {
		return LifecycleEvent.valueOf(getLifecycleEvent());
	}
	
	/**
	 * translated (from enum to string) version of 'set'
	 * @param lifecycleEvent_
	 * @return LifecycleMsg
	 */
	default LifecycleMsg xsetLifecycleEvent(
			final LifecycleEvent lifecycleEvent_) {
		setLifecycleEvent(lifecycleEvent_.toString());
		return this;
	}
	
	static LifecycleMsg create(
			final String lifecycleEvent_) 
					throws Exception {
		return create(msg -> {
			msg.setLifecycleEvent(lifecycleEvent_);
		});
	}
	
	static LifecycleMsg create(
			final LifecycleEvent lifecycleEvent_) 
					throws Exception {
		return create(msg -> {
			msg.xsetLifecycleEvent(lifecycleEvent_);
		});
	}
	
	static LifecycleMsg create(
			ObjectPopulator<LifecycleMsg> objectPopulator_) 
					throws Exception {
		return Framework.getObjectFactory().fabricate(
				LifecycleMsg.class
				, objectPopulator_);
	}
}

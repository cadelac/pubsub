package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.Message;

public interface HasApplicationId extends Message {
	
	String getApplicationId();
	void setApplicationId(String applicationId);
	
	static HasApplicationId create(
			final String applicationId_) 
					throws Exception {
		return Framework.getObjectFactory().fabricate(
				HasApplicationId.class
				, m -> {
					m.setApplicationId(applicationId_);
				});
	}
}

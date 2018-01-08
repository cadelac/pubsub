package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.core.message.Message;

public interface HasApplicationId extends Message {
	String getApplicationId();
	void setApplicationId(String applicationId);
}

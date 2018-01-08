package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.core.message.Message;

public interface HasInitiator extends Message {
	
	HasApplicationId getInitiator();
	void setInitiator(HasApplicationId initiator);
}

package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.core.message.Message;

public interface HasResponder extends Message {

	HasApplicationId getResponder();
	void setResponder(HasApplicationId responder);
}

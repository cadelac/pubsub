package cadelac.framework.pubsub.message.base;

public interface HasResponder {

	HasApplicationId getResponder();
	void setResponder(HasApplicationId responder);
}

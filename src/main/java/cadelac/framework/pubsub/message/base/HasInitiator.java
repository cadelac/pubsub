package cadelac.framework.pubsub.message.base;

public interface HasInitiator {
	
	HasApplicationId getInitiator();
	void setInitiator(HasApplicationId initiator);
}

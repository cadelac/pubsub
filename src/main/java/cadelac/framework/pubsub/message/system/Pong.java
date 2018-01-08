package cadelac.framework.pubsub.message.system;

import cadelac.framework.pubsub.message.AdminMsg;
import cadelac.framework.pubsub.message.base.HasInitiator;
import cadelac.framework.pubsub.message.base.HasResponder;

public interface Pong 
	extends AdminMsg 
	, HasInitiator
	, HasResponder {

	String EVENT = "Pong";
}

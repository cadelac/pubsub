package cadelac.framework.pubsub.message.system;

import cadelac.framework.blade.Framework;
import cadelac.framework.pubsub.message.AdminMsg;
import cadelac.framework.pubsub.message.base.HasApplicationId;
import cadelac.framework.pubsub.message.base.HasInitiator;
import cadelac.framework.pubsub.message.base.HasResponder;

public interface Pong 
	extends AdminMsg 
	, HasInitiator
	, HasResponder {

	String EVENT = "Pong";
	
	static Pong create(
			final HasApplicationId responder
			, final HasApplicationId initiator) 
					throws Exception {
		return Framework.getObjectFactory().fabricate(
				Pong.class
				, p -> {
					p.setInitiator(initiator);
					p.setResponder(responder);
				});
	}
}

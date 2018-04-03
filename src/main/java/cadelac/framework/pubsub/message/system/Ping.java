package cadelac.framework.pubsub.message.system;

import cadelac.framework.blade.Framework;
import cadelac.framework.pubsub.message.AdminMsg;
import cadelac.framework.pubsub.message.base.HasApplicationId;
import cadelac.framework.pubsub.message.base.HasInitiator;
import cadelac.framework.pubsub.message.base.HasResponder;

public interface Ping 
	extends AdminMsg
	, HasInitiator
	, HasResponder {

	String EVENT = "Ping";
	
	static Ping create(
			final HasApplicationId responder
			, final HasApplicationId initiator) 
					throws Exception {
		return Framework.getObjectFactory().fabricate(
				Ping.class
				, p -> {
					p.setInitiator(initiator);
					p.setResponder(responder);
				});
	}
}

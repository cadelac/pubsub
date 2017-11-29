package cadelac.framework.pubsub.message;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.Dispatchable;
import cadelac.framework.blade.core.message.Message;

public interface PayloadMsg extends Message {
	
	default PacketMsg wrap() 
			throws Exception {
		
		@SuppressWarnings("unchecked")
		final Class<? extends Dispatchable> proto = (Class<? extends Dispatchable>)
			Framework.getPrototype2ConcreteMap().prototypeClassOf(this.getClass());
		return PacketMsg.createEventMsg(this, proto.getSimpleName());
	}
}

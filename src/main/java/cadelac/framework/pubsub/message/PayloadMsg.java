package cadelac.framework.pubsub.message;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.message.Dispatchable;
import cadelac.framework.blade.core.message.Message;
import cadelac.framework.blade.core.object.ObjectPopulator;

public interface PayloadMsg extends Message {
	
	default PacketMsg wrap() 
			throws Exception {
		return this.wrap(p -> {});
	}
	
	default PacketMsg wrap(
			final PacketMsg packetMsg_) 
					throws Exception {
		return this.wrap(p -> {
			p.setToken(packetMsg_.getToken());
			p.setReference(packetMsg_.getSequenceId());
			p.setGatewayAddress(packetMsg_.getGatewayAddress());
		});
	}
	
	default PacketMsg wrap(
			final ObjectPopulator<PacketMsg> objectPopulator_) 
					throws Exception {
		
		@SuppressWarnings("unchecked")
		final Class<? extends Dispatchable> proto = (Class<? extends Dispatchable>)
			Framework.getPrototype2ConcreteMap().prototypeClassOf(this.getClass());
		final PacketMsg packetMsg = PacketMsg.createEventMsg(this, proto.getSimpleName());
		if (objectPopulator_ != null)
			objectPopulator_.populate(packetMsg);
		return packetMsg;
	}
}

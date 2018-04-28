package cadelac.framework.pubsub.prog.meter.state;

import cadelac.framework.blade.core.state.StateBase;
import cadelac.framework.pubsub.message.PacketMsg;
import cadelac.framework.pubsub.message.system.Meter;

public class MeterState extends StateBase {

	public MeterState(
			final String origin_
			, String id_) 
					throws Exception {
		super(id_);
		_packet = Meter.create().wrap(p -> {
			p.setOrigin(origin_);
		});
	}
	
	public PacketMsg getPacket() {
		return _packet;
	}

	private final PacketMsg _packet;
}

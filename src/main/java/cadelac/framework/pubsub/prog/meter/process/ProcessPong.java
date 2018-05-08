package cadelac.framework.pubsub.prog.meter.process;

import static cadelac.framework.blade.Framework.$dispatch;

import java.util.Date;

import org.apache.log4j.Logger;

import cadelac.framework.blade.core.Utilities;
import cadelac.framework.blade.core.state.StateId;
import cadelac.framework.pubsub.LifecycleEvent;
import cadelac.framework.pubsub.message.PacketMsg;
import cadelac.framework.pubsub.message.system.Meter;
import cadelac.framework.pubsub.message.system.Pong;
import cadelac.framework.pubsub.prog.meter.state.MeterState;

public class ProcessPong {
	
	public static void process(
			final PacketMsg packet_) 
					throws Exception {
		
		final Pong pong = (Pong) packet_.getPayload();
		
		logger.info(String.format(
				"received message %s from %s at time %s"
				, pong.getClass().getSimpleName()
				, packet_.getOrigin()
				, Utilities.getDateString(
						new Date(packet_.getTimestamp())
						, "HH:mm:ss.SSS")
				));
		
		// build state id
		final String stateId = 
				Common.buildMeterStateId(packet_.getOrigin());
				
		$dispatch.push(
				() -> StateId.build(stateId)
				, () -> Common.createMeterState(packet_.getOrigin(), stateId)
				, (MeterState state_) -> {
					
					Meter meter = (Meter) state_.getPacket().getPayload();
					meter.getPongTime().setTimestamp(
							packet_.getTimestamp());
					Common.setCacheLifecycleStatus(
							LifecycleEvent.HEARTBEAT
							, meter
							, state_);
				});
	}
	
	public static final Logger logger = Logger.getLogger(ProcessPong.class);

}

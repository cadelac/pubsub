package cadelac.framework.pubsub.prog.meter.process;

import java.util.Date;

import org.apache.log4j.Logger;

import static cadelac.framework.blade.Framework.$dispatch;
import cadelac.framework.blade.core.Utilities;
import cadelac.framework.blade.core.state.StateId;
import cadelac.framework.pubsub.LifecycleEvent;
import cadelac.framework.pubsub.message.PacketMsg;
import cadelac.framework.pubsub.message.system.LifecycleMsg;
import cadelac.framework.pubsub.message.system.Meter;
import cadelac.framework.pubsub.prog.meter.state.MeterState;

public class ProcessLifecycleMsg {

	public static void process(
			final PacketMsg packet_) 
					throws Exception {
		
		final LifecycleMsg lifecycleMsg = (LifecycleMsg) packet_.getPayload();
		final LifecycleEvent lifecycleEvent = lifecycleMsg.xgetLifecycleEvent();
		
		logger.info(String.format(
				"received message %s %s from %s at time %s"
				, lifecycleMsg.getClass().getSimpleName()
				, lifecycleEvent.toString()
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

					if (LifecycleEvent.HEARTBEAT ==  lifecycleEvent) {
						meter.getHeartbeatTime().setTimestamp(
								packet_.getTimestamp());
						Common.setCacheLifecycleStatus(
								LifecycleEvent.HEARTBEAT
								, meter
								, state_);
					}
					else if (LifecycleEvent.UP ==  lifecycleEvent) {
						meter.getStartUpTime().setTimestamp(
								packet_.getTimestamp());
						Common.setCacheLifecycleStatus(
								LifecycleEvent.UP
								, meter
								, state_);
					}
				});
	}
	
	
	
	public static final Logger logger = Logger.getLogger(ProcessLifecycleMsg.class);		
}

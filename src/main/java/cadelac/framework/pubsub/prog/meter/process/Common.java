package cadelac.framework.pubsub.prog.meter.process;

import static cadelac.framework.blade.Framework.$dispatch;

import org.apache.log4j.Logger;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.Utilities;
import cadelac.framework.blade.core.state.StateId;
import cadelac.framework.blade.core.state.StatePolicy;
import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.LifecycleEvent;
import cadelac.framework.pubsub.PubSubApp;
import cadelac.framework.pubsub.message.base.HasApplicationId;
import cadelac.framework.pubsub.message.system.Meter;
import cadelac.framework.pubsub.message.system.Ping;
import cadelac.framework.pubsub.prog.meter.state.MeterState;

public class Common {
	
	public static final String METER_KEY_PREFIX = "meter";
	
	public static String buildMeterStateId(
			final String origin_) {
		return METER_KEY_PREFIX + "-" + origin_;
	}
	
	public static MeterState createMeterState(
			final String origin_
			, final String stateId_) 
					throws Exception {
		
		final MeterState meterState = new MeterState(origin_, stateId_);
		BusChannel.putCache(stateId_, meterState.getPacket());

		$dispatch.push(
				PubSubApp.HEARTBEAT_PERIOD
				, () -> {
					recurring(meterState);
				});
		
		return meterState;
	}
	
	public static void doPing(
			final String appName_) 
					throws Exception {
		
		Ping
		.create(
				HasApplicationId.create(appName_) // responder
				, HasApplicationId.create(
						Framework.getApplication().getId())) // initiator
		.wrap().publish(BusChannel.SYSTEM);
	}
	
	public static void setCacheLifecycleStatus(
			LifecycleEvent lifecycleEvent
			, final Meter meter
			, MeterState state_) {
		
		// update cache status
		meter.setLifecycleEvent(
				lifecycleEvent.toString());
		state_.getPacket().setTimestamp(
				Utilities.getTimestamp());
		BusChannel.putCache(
				state_.getId()
				, state_.getPacket());
	}
	
	private static void recurring(
			MeterState state_ ) 
					throws Exception {
		
		Meter meter = (Meter) state_.getPacket().getPayload();
		long heartbeat = meter.getHeartbeatTime().getTimestamp();
		if (heartbeat < 1) {
			// skip if no heartbeat
			logger.info("skipping recurring: no previous heartbeat");
		}
		else {
			long timestampNow = Utilities.getTimestamp();
			long age = timestampNow - heartbeat;
			if (age > PubSubApp.HEARTBEAT_PERIOD) {
				// stale heartbeat
				logger.info("pinging");
				doPing(state_.getPacket().getOrigin());
				if (LifecycleEvent.valueOf(meter.getLifecycleEvent()) != 
						LifecycleEvent.DOWN) {
					setCacheLifecycleStatus(
							LifecycleEvent.DOWN
							, meter
							, state_);
				}
			}
			else {
				logger.info("no need to ping");
				if (LifecycleEvent.valueOf(meter.getLifecycleEvent()) == 
						LifecycleEvent.DOWN) {
					setCacheLifecycleStatus(
							LifecycleEvent.HEARTBEAT
							, meter
							, state_);
				}
			}
		}

		$dispatch.push(
				2 * PubSubApp.HEARTBEAT_PERIOD // delay in milliseconds
				, StatePolicy.MUST_PRE_EXIST
				, () -> StateId.build(state_.getId())
				, () -> null
				, (MeterState state) -> recurring(state));
	}
	
	private static final Logger logger = Logger.getLogger(Common.class);
}

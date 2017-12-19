package cadelac.framework.pubsub;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.app.ApplicationSimple;
import cadelac.framework.blade.core.config.Configurator;
import cadelac.framework.blade.core.exception.FrameworkException;
import cadelac.framework.blade.core.state.StateLess;
import cadelac.framework.blade.v2.core.dispatch.StateId;
import cadelac.framework.pubsub.message.base.HasDuration;
import cadelac.framework.pubsub.message.system.LifecycleMsg;
import de.jackwhite20.japs.client.pub.Publisher;
import de.jackwhite20.japs.client.pub.PublisherFactory;
import de.jackwhite20.japs.client.sub.Subscriber;
import de.jackwhite20.japs.client.sub.SubscriberFactory;

public abstract class PubSubApp extends ApplicationSimple {

	public PubSubApp(
			final String applicationName_
			, final String[] arguments_) {
		super(applicationName_, arguments_);
	}

	@Override
	public void init() 
			throws FrameworkException, Exception {
		
		final BusConfig busConfig = Configurator.getConfigSetting(
				BusConfig.class
				, BusConfig.BUS_SERVER_CONFIG);
		
		BusChannel.setPublisher(createPublisher(busConfig));
		BusChannel.setSubscriber(createSubscriber(getId(), busConfig));
		
		if (shouldSubscribeSystemChannel()) {
			
			// publish LifecycleEvent.UP
//			Framework.getObjectFactory().fabricate(
//					LifecycleMsg.class
//					, event -> {
//						event.setLifecycleEvent(LifecycleEvent.UP.toString());
//					})
			LifecycleMsg.create(LifecycleEvent.UP)
			.wrap()
			.publishOn(BusChannel.SYSTEM);

			
			// create heartbeat
			final LifecycleMsg heartbeat = 
//					Framework.getObjectFactory().fabricate(
//							LifecycleMsg.class
//							, event -> {
//								event.setLifecycleEvent(LifecycleEvent.HEARTBEAT.toString());
//							});
					LifecycleMsg.create(LifecycleEvent.HEARTBEAT);

			// publish periodic heartbeat
			heartbeat
			.stateId(() -> StateId.build(StateLess.STATELESS_STATE_ID))
			.getState(() -> StateLess.STATELESS_STATE)
			.push(HEARTBEAT_PERIOD
					, 0L // delay
					, (StateLess state_) -> {
						heartbeat.wrap().publishOn(BusChannel.SYSTEM);
					});
			
			subscribeSystemChannel();
		}
		
		if (shouldSubscribeScriptChannel())
			subscribeScriptChannel();
	}
	
	protected final static long HEARTBEAT_PERIOD = 15 * HasDuration.ONE_SECOND;
	
	protected boolean shouldSubscribeSystemChannel() { return true; }
	protected boolean shouldSubscribeScriptChannel() { return true; }

	protected abstract void subscribeSystemChannel();
	protected abstract void subscribeScriptChannel();
	
	
	private Publisher createPublisher(final BusConfig busConfig_) {
		return PublisherFactory.create(
				busConfig_.getHost()
				, busConfig_.getPort());
	}
	
	private Subscriber createSubscriber(
			final String subscriberName_
			, final BusConfig busConfig_) {
		return SubscriberFactory.create(
				busConfig_.getHost()
				, busConfig_.getPort()
				, subscriberName_);
	}
}

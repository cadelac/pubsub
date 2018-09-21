package cadelac.framework.pubsub;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static cadelac.framework.blade.Framework.$dispatch;
import cadelac.framework.blade.app.ApplicationSimple;
import cadelac.framework.blade.core.config.Configurator;
import cadelac.framework.blade.core.exception.FrameworkException;
import cadelac.framework.pubsub.message.base.HasDuration;
import cadelac.framework.pubsub.message.system.LifecycleMsg;
import de.jackwhite20.japs.client.cache.PubSubCache;
import de.jackwhite20.japs.client.cache.PubSubCacheFactory;
import de.jackwhite20.japs.client.pub.Publisher;
import de.jackwhite20.japs.client.pub.PublisherFactory;
import de.jackwhite20.japs.client.sub.Subscriber;
import de.jackwhite20.japs.client.sub.SubscriberFactory;
import de.jackwhite20.japs.shared.net.ConnectException;

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
		BusChannel.setPubSubCache(createPubSubCache(busConfig));

		
		initSystemChannel();
		initScriptChannel();
	}
	
	public final static long HEARTBEAT_PERIOD = 5 * HasDuration.ONE_SECOND;
	
	protected boolean shouldSubscribeSystemChannel() { return true; }
	protected boolean shouldSubscribeScriptChannel() { return true; }

	protected boolean shouldPublishLifecycleUP() { return true; }
	protected boolean shouldPublishHeartbeat() { return true; }

	protected void subscribeSystemChannel() {}
	protected void subscribeScriptChannel() {}
	
	
	private void initSystemChannel() throws Exception {
		
		// publish LifecycleEvent.UP
		if (this.shouldPublishLifecycleUP()) 
			LifecycleMsg.create(LifecycleEvent.UP)
			.wrap()
			.audit(BusChannel.SYSTEM.getId())
			.publish(BusChannel.SYSTEM);

		
		if (this.shouldPublishHeartbeat()) {
			// create heartbeat
			final LifecycleMsg heartbeat = 
					LifecycleMsg.create(LifecycleEvent.HEARTBEAT);

			// publish periodic heartbeat
			$dispatch.push(
					HEARTBEAT_PERIOD
					, 0L // delay
					, () -> heartbeat.wrap().publish(BusChannel.SYSTEM));
		}
		
		
		if (this.shouldSubscribeSystemChannel())
			subscribeSystemChannel();

	}
	
	private void initScriptChannel() throws Exception {
		if (shouldSubscribeScriptChannel())
			subscribeScriptChannel();
	}
	
	
	private Publisher createPublisher(
			final BusConfig busConfig_) 
					throws InterruptedException {
		return repeatedAttemptPublisher(busConfig_);
	}
	
	private Subscriber createSubscriber(
			final String subscriberName_
			, final BusConfig busConfig_) 
					throws InterruptedException {
		return repeatedAttemptSubscriber(subscriberName_, busConfig_);
	}
	
	private PubSubCache createPubSubCache(
			final BusConfig busConfig_) 
					throws InterruptedException {
		return repeatedAttemptPubSubCache(busConfig_);
	}
	
	private Publisher repeatedAttemptPublisher(
			final BusConfig busConfig_) 
					throws InterruptedException {
		while (true) {
			try {
				return PublisherFactory.create(
						busConfig_.getHost()
						, busConfig_.getPort());
			}
			catch (ConnectException e_) {
				// sleep and try again
				logger.warn(String.format(
						"failed to create publisher at %s:%d -- will sleep/retry"
						, busConfig_.getHost()
						, busConfig_.getPort()));
				//Utilities.logException(e_, logger);
				Thread.sleep(HEARTBEAT_PERIOD);
			}
		}
	}
	
	private Subscriber repeatedAttemptSubscriber(
			final String subscriberName_
			, final BusConfig busConfig_) 
					throws InterruptedException {
		while (true) {
			try {
				Subscriber subscriber = 
						SubscriberFactory.create(
								busConfig_.getHost()
								, busConfig_.getPort()
								, subscriberName_);
				logger.info(String.format(
						"created subscriber %s at %s:%d"
						, subscriberName_
						, busConfig_.getHost()
						, busConfig_.getPort()));
				return subscriber;
			}
			catch (ConnectException e_) {
				// sleep and try again
				logger.warn(String.format(
						"failed to create subscriber %s at %s:%d -- will sleep/retry"
						, subscriberName_
						, busConfig_.getHost()
						, busConfig_.getPort()));
				//Utilities.logException(e_, logger);
				Thread.sleep(HEARTBEAT_PERIOD);
			}
		}
	}
	
	private PubSubCache repeatedAttemptPubSubCache(
			final BusConfig busConfig_) 
					throws InterruptedException {
		while (true) {
			try {
				return PubSubCacheFactory.create(
						busConfig_.getHost()
						, busConfig_.getPort());
			}
			catch (ConnectException e_) {
				// sleep and try again
				logger.warn(String.format(
						"failed to create cache for %s:%d -- will sleep/retry"
						, busConfig_.getHost()
						, busConfig_.getPort()));				
				//Utilities.logException(e_, logger);
				Thread.sleep(HEARTBEAT_PERIOD);
			}
		}
	}
	
	
	public static final Logger logger = LogManager.getLogger(PubSubApp.class);
}

package cadelac.framework.pubsub;

import cadelac.framework.blade.app.ApplicationSimple;
import cadelac.framework.blade.core.config.Configurator;
import cadelac.framework.blade.core.exception.FrameworkException;
import de.jackwhite20.japs.client.pub.Publisher;
import de.jackwhite20.japs.client.pub.PublisherFactory;
import de.jackwhite20.japs.client.sub.Subscriber;
import de.jackwhite20.japs.client.sub.SubscriberFactory;

public class PubSubApp extends ApplicationSimple {

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
	}
	
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

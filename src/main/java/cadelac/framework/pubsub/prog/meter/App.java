package cadelac.framework.pubsub.prog.meter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import cadelac.framework.blade.core.exception.FrameworkException;
import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.PubSubApp;
import cadelac.framework.pubsub.prog.meter.handler.SystemChannelHandler;

/**
 * Meter
 * @author cadelac
 *
 */
public class App extends PubSubApp {
	
	public static final String APP_NAME = "meterApp";

	public App(final String[] args_) {
		super(APP_NAME, args_);
	}

	
	@Override
	public void init() 
			throws FrameworkException, Exception {
		
		super.init();

		Script.simulate();
	}

	
	protected boolean shouldSubscribeScriptChannel() { return false; }
	protected boolean shouldPublishLifecycleUP() { return false; }
	protected boolean shouldPublishHeartbeat() { return false; }


	protected void subscribeSystemChannel() {
		BusChannel.getSubscriber().subscribeMulti(SystemChannelHandler.class);
	}
	
	
	private static final Logger logger = LogManager.getLogger(App.class);
}

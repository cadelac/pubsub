package cadelac.framework.pubsub.prog.monitor;

import org.apache.log4j.Logger;

import cadelac.framework.blade.core.exception.FrameworkException;
import cadelac.framework.pubsub.BusChannel;
import cadelac.framework.pubsub.LifecycleEvent;
import cadelac.framework.pubsub.PubSubApp;
import cadelac.framework.pubsub.message.MsgDecoder;
import cadelac.framework.pubsub.message.PacketMsg;
import cadelac.framework.pubsub.message.monitor.MonitorMsg;
import cadelac.framework.pubsub.message.system.LifecycleMsg;
import cadelac.framework.pubsub.prog.monitor.handler.MonitorChannelHandler;
import cadelac.framework.pubsub.prog.monitor.handler.ScriptChannelHandler;
import cadelac.framework.pubsub.prog.monitor.handler.SystemChannelHandler;


/**
 * Monitor
 * @author cadelac
 *
 */
public class App extends PubSubApp {

	public static final String APP_NAME = "monitorApp";
	
	public App(final String[] args_) {
		super(APP_NAME, args_);
	}

	
	
	@Override
	public void init() 
			throws FrameworkException, Exception {
		
		super.init();
		
		configureDecoder();
		
		subscribeSystemChannel();
		subscribeMonitorChannel();
		
		Script.simulate();
	}

	protected void subscribeMonitorChannel() {
		BusChannel.subscribe(MonitorChannelHandler.class);
	}

	@Override
	protected void subscribeSystemChannel() {
		BusChannel.getSubscriber().subscribeMulti(SystemChannelHandler.class);

	}
	
	@Override
	protected boolean shouldPublishLifecycleUP() {
		/*
		We are the monitor app (in stealth mode)
		 */
		return false; 
	}

	@Override
	protected boolean shouldPublishHeartbeat() {
		/*
		We are the monitor app (in stealth mode)
		 */
		return false; 
	}


	@Override
	protected void subscribeScriptChannel() {
		BusChannel.getSubscriber().subscribeMulti(ScriptChannelHandler.class);
	}
	
	
	protected void configureDecoder() {

		MsgDecoder.add(
				LifecycleMsg.class
				, packet_ -> handleLifecycle(packet_));
		
		MsgDecoder.add(
				MonitorMsg.class
				, packet_ -> handleMonitorMsg(packet_));
	}


	protected void handleLifecycle(final PacketMsg packet_) throws Exception {
		packet_.getPayload().push(
				() -> {
					final LifecycleMsg msg =
							(LifecycleMsg) packet_.getPayload();
					
					final LifecycleEvent lifecycleEvent = 
							msg.xgetLifecycleEvent();
					if (lifecycleEvent != LifecycleEvent.HEARTBEAT) {
						// ignore heart-beat messages (too much noise!)
						logger.warn(String.format(
								"%d: [%s] %s - %s"
								, packet_.getTimestamp()
								, lifecycleEvent.getClass().getSimpleName()
								, packet_.getOrigin()
								, lifecycleEvent.toString()));
					}
				});
	}
	
	protected void handleMonitorMsg(final PacketMsg packet_) throws Exception {
		packet_.getPayload().push(
				() -> {
					final MonitorMsg mm =
							(MonitorMsg) packet_.getPayload();
					
					logger.warn(String.format(
							"%d: [%s] %s >>> [%s]"
							, packet_.getTimestamp()
							, mm.getClass().getSimpleName()
							, packet_.getOrigin()
							, mm.getText()));
				});
	}
	
	
	private static final Logger logger = Logger.getLogger(App.class);
}

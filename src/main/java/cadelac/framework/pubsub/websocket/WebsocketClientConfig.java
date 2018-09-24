package cadelac.framework.pubsub.websocket;

import javax.websocket.CloseReason;
import javax.websocket.Session;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import cadelac.framework.blade.Framework;

public class WebsocketClientConfig extends WebsocketConfig {

	protected WebsocketClientConfig(final String serverName_) {
		super(serverName_);

	}
	
	protected void onOpenPrologue(final Session websocketSession_) 
			throws Exception {
		logger.info(String.format(
				"We (client:%s) just connected to server %s: websocket session [%s]"
				, Framework.getApplication().getId()
				, getId()
				, websocketSession_.getId()));
	}

	protected void onClosePrologue(
			final Session websocketSession_
			, final CloseReason closeReason) 
					throws Exception {
		logger.info(String.format(
				"Server %s disconnected from us (client:%s): "
					+ "websocket session [%s] was closed because of [%s]"
				, getId()
				, Framework.getApplication().getId()
				, websocketSession_.getId()
				, closeReason));
	}

	
	private static final Logger logger = LogManager.getLogger(WebsocketClientConfig.class);
}

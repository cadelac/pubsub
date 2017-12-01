package cadelac.framework.pubsub.websocket;

import javax.websocket.CloseReason;
import javax.websocket.Session;

import org.apache.log4j.Logger;

/**
 * Base server-side configuration class for Websocket
 * @author cadelac
 *
 */
public class WebsocketServerConfig extends WebsocketConfig {
	
	
	protected WebsocketServerConfig(final String id_) {
		super(id_);
	}
	
	
	protected void onOpenPrologue(final Session websocketSession_) 
			throws Exception {
		logger.info(String.format(
				"A client just connected to us (server:%s): "
					+ "websocket session [%s]"
				, getId()
				, websocketSession_.getId()));
	}
	
	
	protected void onClosePrologue(
			final Session websocketSession_
			, final CloseReason closeReason_) 
					throws Exception {
		logger.info(String.format(
				"A Websocket client disconnected from us (server:%s): " 
						+ "websocket session [%s], reason [%s]"
				, getId()
				, websocketSession_.getId()
				, closeReason_.getReasonPhrase()));
	}

	
	private static final Logger logger = Logger.getLogger(WebsocketServerConfig.class);
}

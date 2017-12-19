package cadelac.framework.pubsub.websocket;

import javax.websocket.Session;

import org.apache.log4j.Logger;

import cadelac.framework.blade.core.Identified;
import cadelac.framework.blade.core.IdentifiedBase;
import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.pubsub.message.PacketMsg;

public class WebsocketConfig 
		extends IdentifiedBase implements Identified {

	public WebsocketConfig(String id_) {
		super(id_);
		logger.info(String.format(
				"created %s for %s"
				, WebsocketConfig.class.getSimpleName()
				, getId()));
	}

	/**
	 * Prepares message for processing by server: 
	 * logs a diagnostic message and 
	 * @param packetRequest_
	 * @param clientWebsocketSession_
	 * @throws Exception 
	 */
	protected void onMessagePrologue(
			final PacketMsg packetRequest_
			, final Session clientWebsocketSession_) 
					throws Exception {

		logger.info(String.format(
				"Received from %s on websocket session [%s]:\n%s"
				, getId()
				, clientWebsocketSession_.getId()
				, JsonFormat.encodeOnly(packetRequest_)));
	}

	private static final Logger logger = Logger.getLogger(WebsocketConfig.class);
}

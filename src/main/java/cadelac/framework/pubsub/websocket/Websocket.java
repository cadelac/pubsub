package cadelac.framework.pubsub.websocket;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import cadelac.framework.blade.comm.websocket.WebsocketConnect;
import cadelac.framework.blade.core.Utilities;
import cadelac.framework.blade.core.message.Message;
import cadelac.framework.pubsub.message.PacketMsg;



public class Websocket extends WebsocketConnect {
	
	/**
	 * Adds timestamp to message and transmits message through websocket.
	 * @param packetMsg_
	 * @param websocketSession_
	 * @throws EncodeException
	 * @throws IOException
	 */
    public static void transmit(
    		final PacketMsg packetMsg_
    		, final Session websocketSession_) 
					throws EncodeException, IOException {
    	
    	packetMsg_.setTimestamp(Utilities.getTimestamp());
    	transmit((Message)packetMsg_, websocketSession_);
	}
    
    public static void transmit(
    		final Message packetMsg_
    		, final Session websocketSession_) 
					throws EncodeException, IOException {

		logger.info(String.format(
				"(websocket) Transmitting message on websocket session [%s]"
				, websocketSessionIdOrNull(websocketSession_)));

		// intentional: we are deliberately not checking for null websocketSession: 
		// better to discover crash immediately during development and testing.
		websocketSession_.getBasicRemote().sendObject(packetMsg_);
	}

    public static void transmit(
    		final String text_
    		, final Session websocketSession_) 
					throws EncodeException, IOException {

		logger.info(String.format(
				"(websocket) Transmitting message on websocket session [%s]\n%s"
				, websocketSessionIdOrNull(websocketSession_)
				, text_));
		
		// intentional: we are deliberately not checking for null websocketSession: 
		// better to discover crash immediately during development and testing.
		websocketSession_.getBasicRemote().sendObject(text_);
	}
    
    
    private static String websocketSessionIdOrNull(final Session websocketSession_) {
    	return (websocketSession_==null) ? "<null>" : websocketSession_.getId();
    }
	
	private static final Logger logger = LogManager.getLogger(Websocket.class);
}

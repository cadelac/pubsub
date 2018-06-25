package cadelac.framework.pubsub.websocket;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.Session;

import org.apache.log4j.Logger;

/**
 * Base server-side configuration class for Websocket
 * @author cadelac
 *
 */
public class WebsocketServerConfig extends WebsocketConfig {
	
	public static class Lookup {

		public Lookup(final Session session_) {
			_session = session_;
		}
		public Session getSession() {
			return _session;
		}

		private Session _session;
	}
	
	
	protected WebsocketServerConfig(final String id_) {
		super(id_);
	}
	
	
	protected void onOpenPrologue(final Session websocketSession_) 
			throws Exception {
		
		final int count = put(websocketSession_);
		
		logger.info(String.format(
				"A client websocket session [%s] " 
				+ "just connected (total=%d) to us (server:%s): "
				, getId()
				, count
				, websocketSession_.getId()));
	}
	
	
	protected void onClosePrologue(
			final Session websocketSession_
			, final CloseReason closeReason_) 
					throws Exception {
		
		final int count = remove(websocketSession_);
		
		logger.info(String.format(
				"A websocket client session [%s] "
				+ "disconnected (total=%d) from us (server:%s): "
				+ "reason [%s]" 
				, getId()
				, count
				, websocketSession_.getId()
				, closeReason_.getReasonPhrase()));
	}
	
	
	private int put(final Session websocketSession_) {
		LOOKUP_MAP.put(
				websocketSession_.getId()
				, new Lookup(websocketSession_));
		return LOOKUP_MAP.size();
	}
	
	public int remove(final Session websocketSession_) {
		LOOKUP_MAP.remove(websocketSession_.getId());
		return LOOKUP_MAP.size();
	}
	
	public Collection<Lookup> getLookups() {
		return LOOKUP_MAP.values();
	}
	
		
	public Session getSession(final String sessionId_) {
		final Lookup lookup = LOOKUP_MAP.get(sessionId_);
		return (lookup==null) ? null : lookup.getSession();
	}
	

	private final Map<String,Lookup> LOOKUP_MAP = 
			new ConcurrentHashMap<String,Lookup>();
	
	
	private static final Logger logger = Logger.getLogger(WebsocketServerConfig.class);
}

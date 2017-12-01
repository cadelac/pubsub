package cadelac.framework.pubsub.message;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.exception.FrameworkException;
import cadelac.framework.blade.core.exception.InitializationException;
import cadelac.framework.pubsub.message.base.HasPayload;


public class MsgDecoder {

	public static PacketMsg decode(JSONObject jsonObject) {
		return decodeInternal(jsonObject);
	}
	
	private static PacketMsg decodeInternal(JSONObject jsonObject) {
		final String json = jsonObject.toString();
		return decode(json);
	}
	
	public static PacketMsg decode(final String json) {
		try {

			final JsonObject jo = 
					Json.createReader(
							new StringReader(json))
					.readObject();

			// create packet
			return Framework.getObjectFactory().fabricate(
					PacketMsg.class
					, p -> {
						p.demarshall(jo);

						if (jo.containsKey(HasPayload.PAYLOAD) 
								&& !jo.isNull(HasPayload.PAYLOAD)) {

							// figure out the class based on the event
							final Symbol<? extends PayloadMsg> symbol =
									SYMBOL_TABLE.get(p.getEvent());

							// sanity checks...
							if (symbol == null || symbol._class == null)
								throw new InitializationException(String.format(
										"Event [%s] not registered with MsgDecoder"
										, p.getEvent()));

							p.setPayload(
									// create payload json object
									Framework.getObjectFactory().fabricate(
											symbol._class
											, q -> {
												q.demarshall(jo.getJsonObject(HasPayload.PAYLOAD));
											})
									);
						}
					}
					);

		}
		catch (Exception e_) {
			logger.error(
					"Exception: " 
							+ e_.getMessage() 
							+ "\nStacktrace:\n" 
							+ FrameworkException.getStringStackTrace(e_));
			return null;
		}		
	}
	
	public static void processMessage(final PacketMsg packet_) 
			throws Exception {
		final Symbol<? extends PayloadMsg> symbol =
				SYMBOL_TABLE.get(packet_.getEvent());
		if (symbol!=null && symbol._class!=null) {
			final MessageProcessor processor = symbol._processor;
			if (processor != null) {
				processor.process(packet_);
			}
		}
		else {
			unexpectedOperation(packet_);
		}
	}
	
	public static <L extends PayloadMsg> void add(
			final Class<L> class_
			, MessageProcessor processor_) {
		final String event_ = class_.getSimpleName();
		SYMBOL_TABLE.put(
				event_ // event_
				, new Symbol<L>(
						event_
						, class_
						, processor_));
	}
	
	public static class Symbol<L extends PayloadMsg> {
		public Symbol(
				final String event_
				, final Class<L> class_
				, final MessageProcessor processor_) {
			_event = event_;
			_class = class_;
			_processor = processor_;
		}
		String _event;
		public Class<L> _class;
		MessageProcessor _processor;
	}
	
	private static void unexpectedOperation(final PacketMsg packet_) {
		logger.warn(String.format(
				"Unexpected Event: [%s]"
				, packet_.getEvent()));
	}
	
	private static final Map<String,Symbol<? extends PayloadMsg>> SYMBOL_TABLE = 
			new HashMap<String,Symbol<? extends PayloadMsg>>();
	
	private static final Logger logger = Logger.getLogger(MsgDecoder.class);
}

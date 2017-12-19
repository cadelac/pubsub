package cadelac.framework.pubsub.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import cadelac.framework.blade.core.message.json.JsonFormat;
import cadelac.framework.pubsub.message.PacketMsg;

public class WSEncoder implements Encoder.Text<PacketMsg> {

	public WSEncoder() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public String encode(final PacketMsg message_) 
			throws EncodeException {
		return JsonFormat.encodeOnly(message_);
	}
}

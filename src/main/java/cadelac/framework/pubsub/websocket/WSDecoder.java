package cadelac.framework.pubsub.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import cadelac.framework.blade.core.message.json.JsonDecoder;
import cadelac.framework.pubsub.message.MsgDecoder;
import cadelac.framework.pubsub.message.PacketMsg;

public class WSDecoder implements Decoder.Text<PacketMsg> {

	public WSDecoder() {}

	@Override
	public void destroy() {}

	@Override
	public void init(EndpointConfig arg0) {}

	@Override
	public PacketMsg decode(final String message_) 
			throws DecodeException {
		return MsgDecoder.decode(message_);
	}
	
	@Override
	public boolean willDecode(String message_) {
		return JsonDecoder.willDecode(message_);
	}
}

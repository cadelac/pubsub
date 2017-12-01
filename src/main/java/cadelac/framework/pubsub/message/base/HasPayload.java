package cadelac.framework.pubsub.message.base;

import cadelac.framework.pubsub.message.PayloadMsg;

public interface HasPayload {
	String PAYLOAD = "Payload";
	
	PayloadMsg getPayload();
	void setPayload(PayloadMsg payload);
}

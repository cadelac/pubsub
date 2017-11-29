package cadelac.framework.pubsub;

import org.json.JSONObject;

public abstract class ChannelHandler {
	
	public static final String EVENT_KEY = "Event";
	
	protected void process(
			final JSONObject jsonObject
			, final String eventString_) 
					throws Exception {
		
		jsonObject.put(EVENT_KEY, eventString_);
    	specialProcess(jsonObject, eventString_);
	}
	
	protected abstract void specialProcess(
			final JSONObject jsonObject
			, final String eventString_) throws Exception;
	
	protected abstract String getChannelName();
}

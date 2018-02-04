package cadelac.framework.pubsub.message.monitor;

import cadelac.framework.blade.Framework;
import cadelac.framework.pubsub.message.PayloadMsg;
import cadelac.framework.pubsub.message.base.HasText;

public interface MonitorMsg 
		extends PayloadMsg
		, HasText {
	
	String EVENT = "MonitorMsg";

	static MonitorMsg create(final String text_)
			throws Exception {
		
		return Framework.getObjectFactory().fabricate(
				MonitorMsg.class
				, monitor -> { 
					monitor.setText(text_); 
				});
	}
}

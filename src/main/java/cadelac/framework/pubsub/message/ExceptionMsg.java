package cadelac.framework.pubsub.message;

import cadelac.framework.blade.Framework;
import cadelac.framework.pubsub.message.base.HasText;

public interface ExceptionMsg 
		extends PayloadMsg
		, HasText {
	String EVENT = "ExceptionMsg";
	
	static ExceptionMsg create(final String exceptionText_) 
			throws Exception {
		return Framework.getObjectFactory().fabricate(
				ExceptionMsg.class
				, except -> {
					except.setText(exceptionText_);
				});
	}
}

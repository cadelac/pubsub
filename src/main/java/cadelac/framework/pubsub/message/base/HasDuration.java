package cadelac.framework.pubsub.message.base;

import cadelac.framework.blade.core.message.Message;

/**
 * Time duration measured in milliseconds
 * @author cadelac
 *
 */
public interface HasDuration extends Message {

	long ONE_SECOND = 1000L; // is a thousand milliseconds
	long ONE_MINUTE = 60 * ONE_SECOND;
	long ONE_HOUR = 60 * ONE_MINUTE;
	long ONE_DAY = 24 * ONE_HOUR;
	
	long getDuration();
	void setDuration(long duration);
	
}

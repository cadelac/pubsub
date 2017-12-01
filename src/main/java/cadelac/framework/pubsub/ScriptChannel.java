package cadelac.framework.pubsub;

import cadelac.framework.blade.Framework;
import cadelac.framework.blade.core.object.ObjectPopulator;
import cadelac.framework.pubsub.message.PayloadMsg;

public class ScriptChannel extends BusChannel {

	public static <T extends PayloadMsg> Internal create(
			Class<T> class_
			, ObjectPopulator<T> objectPopulator_) 
					throws Exception {
		return new Internal(
				Framework.getObjectFactory().fabricate(class_, objectPopulator_)
				, BusChannel.SCRIPT);
	}
}

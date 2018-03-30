package cadelac.framework.pubsub.generator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Message type
 * @author cadelac
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Input {
	Class<?> type();
	Class<?> handler();
}

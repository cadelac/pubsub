package cadelac.framework.pubsub.generator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Array of accepted inputs
 * @author cadelac
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Accept {
	Input[] value();
}

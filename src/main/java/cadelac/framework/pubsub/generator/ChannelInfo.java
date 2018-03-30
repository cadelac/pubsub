package cadelac.framework.pubsub.generator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Channel Description
 * @author cadelac
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ChannelInfo {
	String value();
	String classname() default "";

}

package cadelac.framework.pubsub.message.base;

/**
 * Refers to a related message
 * @author cadelac
 *
 */
public interface HasReference {
	long getReference();
	void setReference(long reference);
}

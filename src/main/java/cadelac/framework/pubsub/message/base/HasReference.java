package cadelac.framework.pubsub.message.base;

/**
 * Used to relate a message to something
 * @author cadelac
 *
 */
public interface HasReference {
	String getReference();
	void setReference(String reference);
}

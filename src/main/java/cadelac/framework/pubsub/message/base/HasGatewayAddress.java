package cadelac.framework.pubsub.message.base;

/**
 * Gateway address translation
 * @author cadelac
 *
 */
public interface HasGatewayAddress {
	String getGatewayAddress();
	void setGatewayAddress(String gatewayAddress);
}

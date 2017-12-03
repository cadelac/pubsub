package cadelac.framework.pubsub.message.base;

/**
 * Indicates success, failure or exception
 * @author cadelac
 *
 */
public interface HasResult {
	
	/**
	 * 
	 * @return result valid values are: true, false, exception
	 */
	String getResult();
	/**
	 * 
	 * @param result valid values are: true, false, exception
	 */
	void setResult(String result);
}

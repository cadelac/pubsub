package cadelac.framework.pubsub;

import cadelac.framework.blade.core.config.ConfigSetting;

public interface BusConfig extends ConfigSetting {

	String BUS_SERVER_CONFIG = "bus-server-config";
	
	public String getHost();
	public void setHost(String host);

	public int getPort();
	public void setPort(int port);
	
}

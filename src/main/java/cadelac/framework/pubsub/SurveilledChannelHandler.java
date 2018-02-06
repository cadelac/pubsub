package cadelac.framework.pubsub;

public abstract class SurveilledChannelHandler extends BareChannelHandler {

	protected void surveil(
			final String formattedText) 
					throws Exception {
		Utility.monitor(formattedText);
	}
}

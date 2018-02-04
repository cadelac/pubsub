package cadelac.framework.pubsub.prog.monitor;

import cadelac.framework.blade.core.exception.FrameworkException;

public class Main {
	
	public static void main(final String[] args) 
			throws FrameworkException, Exception {

		final App app = new App(args);
		app.start();
	}
}

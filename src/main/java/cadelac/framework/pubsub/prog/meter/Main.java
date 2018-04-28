package cadelac.framework.pubsub.prog.meter;

import cadelac.framework.blade.core.exception.FrameworkException;

public class Main {
	
	public static void main(final String[] args) 
			throws FrameworkException, Exception {
		final App app = new App(args);
		app.start();
	}
}

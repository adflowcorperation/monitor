package kr.co.adflow.monitor.exception;

import org.apache.log4j.Logger;

public class InterceptException extends Exception {

	private static Logger logger = Logger.getLogger(InterceptException.class);

	// private static InterceptException exception = new InterceptException();
	//
	// private InterceptException() {
	//
	// }
	//
	// /* Static 'instance' method */
	// public static InterceptException getInstance() {
	// return exception;
	// }

	private String threadId;

	public InterceptException() {
		// TODO Auto-generated constructor stub
	}

	public InterceptException(String threadId) {

		threadId = threadId;

	}

}

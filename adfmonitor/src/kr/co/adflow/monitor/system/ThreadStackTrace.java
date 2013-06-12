package kr.co.adflow.monitor.system;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadStackTrace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread test = new Test();
		test.start();

		ThreadMXBean threadmbean = ManagementFactory.getThreadMXBean();

		System.out.println("Test Class ThreadID : " + test.getId());
		long threadID = test.getId();

		long ID = threadmbean.getThreadInfo(threadID).getThreadId();
		System.out.println("ThreadID :" + ID);

		System.out.println("stack lensgth: "
				+ threadmbean.getThreadInfo(ID, Integer.MAX_VALUE)
						.getStackTrace().length);

		System.out
				.println("===============TestClass Thread stack trace test=====================");
		StackTraceElement[] tr = test.getStackTrace();
		for (StackTraceElement list : tr) {
			System.out.println("methodName : " + list.getClassName());
			System.out.println("className : " + list.getMethodName());

		}

		System.out
				.println("=============ThreadStackTraceClass Thread stack trace========================");
		ThreadInfo tr2 = threadmbean.getThreadInfo(threadID, Integer.MAX_VALUE);

		StackTraceElement[] ste1 = tr2.getStackTrace();
		for (StackTraceElement list2 : ste1) {
			System.out.println("methodName : " + list2.getClassName());
			System.out.println("className : " + list2.getMethodName());
		}

		// System.out
		// .println("================dumpAllThreads========================");
		// ThreadInfo[] threads = ManagementFactory.getThreadMXBean()
		// .dumpAllThreads(true, true);
		// for (final ThreadInfo info : threads)
		// System.out.print("dumpAllThreads :" + info);// TODO Auto-generated
		// // method stub

	}

}

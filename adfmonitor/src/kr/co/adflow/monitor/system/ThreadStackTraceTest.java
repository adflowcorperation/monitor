package kr.co.adflow.monitor.system;

import java.io.File;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;

import java.lang.management.OperatingSystemMXBean;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import java.text.NumberFormat;
import java.util.List;

public class ThreadStackTraceTest {

	public static void main(String[] args) {
		Thread test = new Test();
		test.start();

		ThreadMXBean threadmbean = ManagementFactory.getThreadMXBean();

		System.out.println("main?�서 ?�오??ID : " + test.getId());
		long threadID = test.getId();

		long ID = threadmbean.getThreadInfo(threadID).getThreadId();
		System.out.println("ID :" + ID);
		System.out.println("?�보�?�?��?�다 : " + threadmbean.getThreadInfo(ID));

		System.out.println("stack ?�보 : "
				+ threadmbean.getThreadInfo(ID, Integer.MAX_VALUE)
						.getStackTrace().length);

		System.out
				.println("===============Thread stack trace test=====================");
		StackTraceElement[] tr = test.getStackTrace();
		for (StackTraceElement list : tr) {
			System.out.println("methodName : " + list.getClassName());
			System.out.println("className : " + list.getMethodName());

		}

		System.out
				.println("=============Thread stack trace========================");
		ThreadInfo tr2 = threadmbean.getThreadInfo(threadID, Integer.MAX_VALUE);// .getStackTrace();

		StackTraceElement[] ste1 = tr2.getStackTrace();
		for (StackTraceElement list2 : ste1) {
			System.out.println("methodName : " + list2.getClassName());
			System.out.println("className : " + list2.getMethodName());
		}

		System.out
				.println("================dumpAllThreads========================");
		ThreadInfo[] threads = ManagementFactory.getThreadMXBean()
				.dumpAllThreads(true, true);
		for (final ThreadInfo info : threads)
			System.out.print("dumpAllThreads :" + info);
		System.out.println("====================OS버전=======================");
		String OSName = showOSBean();
	}
		// System.out
		// .println("========================HDD ?�용??=====================");
		// // StatsdClient sc = new StatsdClient("192.168.0.135", 8125);
		// File[] roots = File.listRoots();
		// float using = 0;
		// float total = 0;
		// float percentage = 0;
		// NumberFormat nf = NumberFormat.getInstance();
		// nf.setMaximumFractionDigits(2);
		// System.out.println(roots.length);
		// for (int i = 0; i < roots.length; i++) {
		// System.out.println(roots[i]);
		//
		// System.out.println("�??�량 = " + roots[i].getTotalSpace());
		// System.out.println("?��? 공간 = " + roots[i].getFreeSpace());
		// System.out.println("?�용 공간 = "
		// + (roots[i].getTotalSpace() - roots[i].getFreeSpace()));
		// total = roots[i].getTotalSpace();
		// using = roots[i].getTotalSpace() - roots[i].getFreeSpace();
		//
		// System.out.println("percentage : "
		// + nf.format((using / total) * 100) + "%");
		// }
		//
		// System.out
		// .println("========================CPU ?�용??=====================");
		// java.lang.management.OperatingSystemMXBean o = ManagementFactory
		// .getOperatingSystemMXBean();
		//
		// System.out
		// .println("========================memory ?�용??=====================");
		//
		// System.out.println("DUMPING MEMORY INFO");
		// // Read MemoryMXBean
		// MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
		// System.out.println("Heap Memory Usage: "
		// + memorymbean.getHeapMemoryUsage());
		// System.out.println("Non-Heap Memory Usage: "
		// + memorymbean.getNonHeapMemoryUsage());
		// memorymbean.

		// Read Garbage Collection information
		// List<GarbageCollectorMXBean> Listgcmbeans = ManagementFactory
		// .getGarbageCollectorMXBeans();
		// for (GarbageCollectorMXBean gcmbean : Listgcmbeans) {
		// System.out.println("\nName: " + gcmbean.getName());
		// System.out.println("Collection count: "
		// + gcmbean.getCollectionCount());
		// System.out.println("Collection time: "
		// + gcmbean.getCollectionTime());
		// System.out.println("Memory Pools: ");
		// String[] memoryPoolNames = gcmbean.getMemoryPoolNames();
		// for (int i = 0; i < memoryPoolNames.length; i++) {
		// System.out.println("\t" + memoryPoolNames[i]);
		// }

//		System.out
//				.println("========================memory2 ?�용??=====================");
//		// // Read Memory Pool Information
//		System.out.println("Memory Pools Info");
//		List<MemoryPoolMXBean> Listmempoolsmbeans = ManagementFactory
//				.getMemoryPoolMXBeans();
//		for (MemoryPoolMXBean mempoolmbean : Listmempoolsmbeans) {
//			System.out.println("\nName: " + mempoolmbean.getName());
//			System.out.println("Usage: " + mempoolmbean.getUsage());
//			System.out.println("Collection Usage: "
//					+ mempoolmbean.getCollectionUsage());
//			System.out.println("Peak Usage: " + mempoolmbean.getPeakUsage());
//			System.out.println("Type: " + mempoolmbean.getType());
//			System.out.println("Memory Manager Names: ");
//			String[] memManagerNames = mempoolmbean.getMemoryManagerNames();
//			for (int i = 0; i < memManagerNames.length; i++) {
//				System.out.println("\t" + memManagerNames[i]);
//			}
//			System.out.println("\n");
//		}
//
//	}

	public static String showOSBean() {
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		System.out.println("OS Name: " + osbean.getName());
		System.out.println("OS Arch: " + osbean.getArch());
		System.out.println("OS Version: " + osbean.getVersion());
		// System.out.println( "Available Processors: " +
		// osbean.getAvailableProcessors( ) );
		// System.out.println( "TotalPhysicalMemorySize: " +
		// osbean.getTotalPhysicalMemorySize( ) );
		// System.out.println( "FreePhysicalMemorySize: " +
		// osbean.getFreePhysicalMemorySize( ) );
		// System.out.println( "TotalSwapSpaceSize: " +
		// osbean.getTotalSwapSpaceSize( ) );
		// System.out.println( "FreeSwapSpaceSize: " +
		// osbean.getFreeSwapSpaceSize( ) );
		// System.out.println( "CommittedVirtualMemorySize: " +
		// osbean.getCommittedVirtualMemorySize( ) );
		// System.out.println( "SystemLoadAverage: " +
		// osbean.getSystemLoadAverage( ) );
		return osbean.getName();
	}

}

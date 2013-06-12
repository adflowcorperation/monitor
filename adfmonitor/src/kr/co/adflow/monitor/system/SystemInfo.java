package kr.co.adflow.monitor.system;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.text.NumberFormat;

import kr.co.adflow.monitor.statsd.StatsdClient;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SystemInfo extends Thread {

	private static Sigar sigar = new Sigar();
	private static StatsdClient sc = StatsdClient.getChanInstance();
	private static OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory
			.getOperatingSystemMXBean();

	/**
	 * @param args
	 * @throws Exception
	 */

	private static SystemInfo systemInfo = new SystemInfo();

	private SystemInfo() {

	}

	public static SystemInfo getSysteminfoInstance() {
		return systemInfo;
	}

	public void test() throws Exception {

		while (true) {
			sleep(3000);
			System.out.println("********CPU**********");
			getCpuInfo();
			System.out.println("********HDD**********");
			getHDDInfo();
			System.out.println("********HMem**********");
			getHeapMemInfo();
			System.out.println("********LMem**********");
			getLocalMemInfo();
			System.out.println("********NHMem**********");
			getNonHeapMemInfo();
			getNonHeapMemInfo2();
		}
	}

	public void getCpuInfo() throws SigarException {

		System.out.println("CPUINFO");
		CpuPerc cpu = sigar.getCpuPerc();
		System.out.println("User Time....." + CpuPerc.format(cpu.getUser()));
		System.out.println("Sys Time......" + CpuPerc.format(cpu.getSys()));
		System.out.println("Idle Time....." + CpuPerc.format(cpu.getIdle()));
		System.out.println("Wait Time....." + CpuPerc.format(cpu.getWait()));
		System.out.println(("Nice Time....." + CpuPerc.format(cpu.getNice())));
		System.out
				.println("Combined......" + CpuPerc.format(cpu.getCombined()));
		System.out.println("Irq Time......" + CpuPerc.format(cpu.getIdle()));

		sc.gauge("kr.co.adflow.cpu." + osbean.getName(),
				cpu.getCombined() * 100);
	}

	public void getLocalMemInfo() throws SigarException {

		Mem mem;

		mem = sigar.getMem();
		System.out.println("Total =" + (mem.getTotal() / 1024) / 1024 + "M av");
		System.out.println("Used =" + (mem.getUsed() / 1024) / 1024 + "M used");
		System.out.println("Free =" + (mem.getFree() / 1024) / 1024 + "M free");
		sc.gauge("kr.co.adflow.mem.local." + osbean.getName(),
				(mem.getUsed() / 1024) / 1024);

	}

	public void getNonHeapMemInfo() throws Exception {

		long heap = getHeapMemInfo();
		long pid = sigar.getPid();
		System.out.println(pid);

		System.out.println("Name = " + sigar.getProcState(pid).getName()
				+ ", pid = " + pid + ", ResidentMem = "
				+ sigar.getProcMem(pid).getResident());
		System.out.println(sigar.getProcMem(pid).getResident() - heap);
		// sc.gauge("kr.co.adflow.mem.non-heap." + osbean.getName(), sigar
		// .getProcMem(pid).getResident());

	}

	public long getHeapMemInfo() throws Exception {
		MemoryMXBean memorymbean = null;
		try {
			memorymbean = ManagementFactory.getMemoryMXBean();
			System.out.println("Heap Memory Usage: "
					+ memorymbean.getHeapMemoryUsage().getUsed());
			sc.gauge("kr.co.adflow.mem.heap." + osbean.getName(), memorymbean
					.getHeapMemoryUsage().getUsed());
		} catch (Exception e) {
			throw e;
		}
		return memorymbean.getHeapMemoryUsage().getUsed();

	}

	public void getNonHeapMemInfo2() throws Exception {

		MemoryMXBean memorymbean = null;
		try {
			memorymbean = ManagementFactory.getMemoryMXBean();
			System.out.println("Non-Heap Memory Usage: "
					+ memorymbean.getNonHeapMemoryUsage().getUsed());
			sc.gauge("kr.co.adflow.mem.non-heap." + osbean.getName(),
					memorymbean.getNonHeapMemoryUsage().getUsed());
		} catch (Exception e) {
			throw e;
		}

	}

	public void getHDDInfo() throws Exception {
		NumberFormat nf = null;
		try {
			File[] roots = File.listRoots();
			float using = 0;
			float total = 0;
			float percentage = 0;
			nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
			System.out.println(roots.length);
			for (int i = 0; i < roots.length; i++) {
				if (roots[i].getTotalSpace() > 0) {
					System.out.println(roots[i]);
					System.out.println("Total = " + roots[i].getTotalSpace());
					System.out.println("Free = " + roots[i].getFreeSpace());
					System.out.println("Used = "
							+ (roots[i].getTotalSpace() - roots[i]
									.getFreeSpace()));
					total = roots[i].getTotalSpace();
					using = roots[i].getTotalSpace() - roots[i].getFreeSpace();

					System.out.println("percentage : "
							+ nf.format((using / total) * 100) + "%");
					percentage = (using / total) * 100;
					sc.gauge("kr.co.adflow.hdd." + osbean.getName() + "."
							+ roots[i], percentage);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}

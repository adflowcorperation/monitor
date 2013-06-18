package kr.co.adflow.monitor.system;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.text.NumberFormat;

import kr.co.adflow.monitor.statsd.StatsdClient;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SystemInfo extends Thread {

	private static Sigar sigar = new Sigar();
	private static StatsdClient sc = StatsdClient.getChanInstance();
	private static OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory
			.getOperatingSystemMXBean();
	private static Logger logger = Logger.getLogger(SystemInfo.class);

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

	public void getSysInfo() throws Exception {

		while (true) {
			sleep(3000);
			logger.info("********CPU**********");
			getCpuInfo();
			logger.info("********HDD**********");
			getHDDInfo();
			logger.info("********HMem**********");
			getHeapMemInfo();
			logger.info("********LMem**********");
			getLocalMemInfo();
			logger.info("********NHMem**********");
			//getNonHeapMemInfo();
			getNonHeapMemInfo2();
		}
	}

	public void getCpuInfo() throws SigarException {

		CpuPerc cpu = sigar.getCpuPerc();
		logger.info("User Time....." + CpuPerc.format(cpu.getUser()));
		logger.info("Sys Time......" + CpuPerc.format(cpu.getSys()));
		logger.info("Idle Time....." + CpuPerc.format(cpu.getIdle()));
		logger.info("Wait Time....." + CpuPerc.format(cpu.getWait()));
		logger.info(("Nice Time....." + CpuPerc.format(cpu.getNice())));
		logger.info("Combined......" + CpuPerc.format(cpu.getCombined()));
		logger.info("Irq Time......" + CpuPerc.format(cpu.getIdle()));

		sc.gauge("kr.co.adflow.cpu." + osbean.getName(),
				cpu.getCombined() * 100);
	}

	public void getLocalMemInfo() throws SigarException {

		Mem mem;

		mem = sigar.getMem();
		logger.info("Total =" + (mem.getTotal() / 1024) / 1024 + "M av");
		logger.info("Used =" + (mem.getUsed() / 1024) / 1024 + "M used");
		logger.info("Free =" + (mem.getFree() / 1024) / 1024 + "M free");
		sc.gauge("kr.co.adflow.mem.local." + osbean.getName(),
				(mem.getUsed() / 1024) / 1024);
		
	}

	public void getNonHeapMemInfo() throws Exception {

		long heap = getHeapMemInfo();
		long pid = sigar.getPid();
		System.out.println(pid);

		logger.info("Name = " + sigar.getProcState(pid).getName()
				+ ", pid = " + pid + ", ResidentMem = "
				+ sigar.getProcMem(pid).getResident());
		logger.info(sigar.getProcMem(pid).getResident() - heap);
		// sc.gauge("kr.co.adflow.mem.non-heap." + osbean.getName(), sigar
		// .getProcMem(pid).getResident());

	}

	public long getHeapMemInfo() throws Exception {
		MemoryMXBean memorymbean = null;
		try {
			memorymbean = ManagementFactory.getMemoryMXBean();
			logger.info("Heap Memory Usage: "
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
			logger.info("Non-Heap Memory Usage: "
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
					logger.info(roots[i]);
					logger.info("Total = " + roots[i].getTotalSpace());
					logger.info("Free = " + roots[i].getFreeSpace());
					logger.info("Used = "
							+ (roots[i].getTotalSpace() - roots[i]
									.getFreeSpace()));
					total = roots[i].getTotalSpace();
					using = roots[i].getTotalSpace() - roots[i].getFreeSpace();

					logger.info("percentage : "
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

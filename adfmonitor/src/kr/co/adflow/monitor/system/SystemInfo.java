package kr.co.adflow.monitor.system;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SystemInfo {
	Sigar sigar = new Sigar();

	/**
	 * @param args
	 * @throws SigarException
	 */
	public static void main(String[] args) throws Exception {
		final SystemInfo si2 = new SystemInfo();
		Timer timer = new Timer();
		final StatsdClient sc = new StatsdClient("192.168.0.135", 8125);
		final OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				try {
					double cpu = si2.getCpuInfo();
					long lmem = si2.getLocalMemInfo();
					long hmem=si2.getHeapMemInfo();
					long nhmem=si2.getNonHeapMemInfo();
					sc.gauge("kr.co.adflow.cpu." + osbean.getName(), cpu, 1.0);
					sc.gauge("kr.co.adflow.mem.local." + osbean.getName(), lmem, 1.0);
					sc.gauge("kr.co.adflow.mem.heap." + osbean.getName(), hmem, 1.0);
					sc.gauge("kr.co.adflow.mem.non-heap." + osbean.getName(), nhmem, 1.0);
					File[] roots = File.listRoots();
					float using = 0;
					float total = 0;
					float percentage = 0;
					NumberFormat nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(0);
					System.out.println(roots.length);
					for (int i = 0; i < roots.length; i++) {
						if (roots[i].getTotalSpace() > 0) {
							System.out.println(roots[i]);
							System.out.println("총 용량 = "
									+ roots[i].getTotalSpace());
							System.out.println("남은 공간 = "
									+ roots[i].getFreeSpace());
							System.out.println("사용 공간 = "
									+ (roots[i].getTotalSpace() - roots[i]
											.getFreeSpace()));
							total = roots[i].getTotalSpace();
							using = roots[i].getTotalSpace()
									- roots[i].getFreeSpace();

							System.out.println("percentage : "
									+ nf.format((using / total) * 100) + "%");
							percentage = (using / total) * 100;
							sc.gauge("kr.co.adflow.hdd." + osbean.getName()
									+ "." + roots[i], percentage, 1.0);
						}
					}
				} catch (SigarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	public double getCpuInfo() throws SigarException {
		CpuPerc cpu = sigar.getCpuPerc();
		System.out.println("User Time....." + CpuPerc.format(cpu.getUser()));
		System.out.println("Sys Time......" + CpuPerc.format(cpu.getSys()));
		System.out.println("Idle Time....." + CpuPerc.format(cpu.getIdle()));
		System.out.println("Wait Time....." + CpuPerc.format(cpu.getWait()));
		System.out.println(("Nice Time....." + CpuPerc.format(cpu.getNice())));
		System.out
				.println("Combined......" + CpuPerc.format(cpu.getCombined()));
		System.out.println("Irq Time......" + CpuPerc.format(cpu.getIrq()));
		// if (SigarLoader.IS_LINUX) {
		// System.out.println("SoftIrq Time.."
		// + CpuPerc.format(cpu.getSoftIrq()));
		// System.out.println("Stolen Time...."
		// + CpuPerc.format(cpu.getStolen()));
		// }
		System.out.println("");
		return cpu.getCombined() * 100;
	}

	public long getLocalMemInfo() throws SigarException {

		Mem mem;

		mem = sigar.getMem();
		System.out.println("Total =" + (mem.getTotal() / 1024) / 1024 + "M av");
		System.out.println("Used =" + (mem.getUsed() / 1024) / 1024 + "M used");
		System.out.println("Free =" + (mem.getFree() / 1024) / 1024 + "M free");
		return (mem.getUsed() / 1024) / 1024;

	}

	public long getHeapMemInfo() {
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
		System.out.println("Heap Memory Usage: "
				+ memorymbean.getHeapMemoryUsage().getUsed());
		return memorymbean.getHeapMemoryUsage().getUsed();
	}
	public long getNonHeapMemInfo() {
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
		System.out.println("Heap Memory Usage: "
				+ memorymbean.getHeapMemoryUsage().getUsed());
		return memorymbean.getHeapMemoryUsage().getUsed();
	}
}

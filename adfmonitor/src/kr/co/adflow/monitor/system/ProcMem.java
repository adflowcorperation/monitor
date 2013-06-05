package kr.co.adflow.monitor.system;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import org.hyperic.sigar.jmx.SigarProcess;
import org.hyperic.sigar.ptql.ProcessFinder;

public class ProcMem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SigarProcess sp = new SigarProcess();

		System.out.println(sp.getMemResident());
		System.out.println(sp.getCpuUsage());
		System.out.println(sp.getMemPageFaults());
		System.out.println(sp.getMemShare());
		System.out.println(sp.getMemSize());

		System.out.println("-------------------------------------------------");
		Sigar sigar = new Sigar();
		long pid = sigar.getPid();
		try {
			long[] pids = sigar.getProcList();

			for (int i = 0; i < pids.length; i++) {
				System.out.println("Name = "
						+ sigar.getProcState(pids[i]).getName() + ", pid = "
						+ pids[i] + ", MemSize = "
						+ sigar.getProcMem(pids[i]).getSize());

			}
			System.out.println(pids.length);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

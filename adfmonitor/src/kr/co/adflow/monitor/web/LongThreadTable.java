package kr.co.adflow.monitor.web;

import java.util.HashSet;
import java.util.Hashtable;

public class LongThreadTable {

	private static final LongThreadTable LONG_THREAD_TABLE = new LongThreadTable();
	private static Hashtable threadHt = new Hashtable();
	private static HashSet set=new HashSet();

	private LongThreadTable() {

	}

	public static LongThreadTable getInstantce() {
		return LONG_THREAD_TABLE;
	}

	public static Hashtable getThreadHt() {
		return threadHt;
	}

	public static void setThreadHt(Hashtable threadHt) {
		LongThreadTable.threadHt = threadHt;
	}

	public static HashSet getSet() {
		return set;
	}

	public static void setSet(HashSet set) {
		LongThreadTable.set = set;
	}





}

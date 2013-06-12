package kr.co.adflow.monitor.system;

public class Test extends Thread {

	public void run() {
		test1();
	}

	public void test1() {
		test2();
	}

	public void test2() {
		// for (int i = 0; i < 3; i++) {
		// if (i == 2) {
		// test3();
		// }
		// }
		while(true){}
		//int i= 1+2;
		//test3();
	}

	public void test3() {
		//System.out.println("dd");
	}
}

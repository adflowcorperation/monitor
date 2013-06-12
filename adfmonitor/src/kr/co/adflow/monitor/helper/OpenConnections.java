package kr.co.adflow.monitor.helper;

import java.util.Enumeration;
import java.util.Hashtable;

public class OpenConnections extends Thread {

	private static OpenConnections openConnections = new OpenConnections();
	private static Hashtable cpendConnht = new Hashtable();

	private OpenConnections() {
		this.start();
	}

	/* Static 'instance' method */
	public static OpenConnections getInstance() {
		return openConnections;
	}

	public static Hashtable getCpendConnht() {
		return cpendConnht;
	}

	public static void setCpendConnht(Hashtable cpendConnht) {
		OpenConnections.cpendConnht = cpendConnht;
	}

	@Override
	public void run() {
		while (true) {
			

			// get openedConnections
			OpenConnections openedConnections = OpenConnections.getInstance();
			//
			Hashtable ht = openedConnections.getCpendConnht();
			System.out.println("ht.size() : "+ht.size());
			// �ð�üũ��������
			long currentTime = System.currentTimeMillis();
			
			for (Enumeration e = ht.keys(); e.hasMoreElements();) {
				long threadID = (Long) e.nextElement();
				System.out.println("threadID : " + threadID);
				long startTime = (Long) ht.get(threadID);
				long elapsedTime = currentTime - startTime;
				System.out.println("elapsedTime : " + elapsedTime);
				// �ɸ��ð��� ���� �б�
				if (elapsedTime < 1000 ) {
					// ����
					// pass
				} else {
					System.out.println(" connectionLeak ");
					// connectionLeak �ϰ��
					// statd �� ����
				}

			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}

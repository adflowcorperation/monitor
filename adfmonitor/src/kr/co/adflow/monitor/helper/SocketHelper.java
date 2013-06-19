package kr.co.adflow.monitor.helper;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Hashtable;

import kr.co.adflow.monitor.statsd.StatsdClient;

import org.apache.log4j.Logger;
import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class SocketHelper extends Helper {

	private static Hashtable hash = new Hashtable();
	private static StatsdClient client = StatsdClient.getChanInstance();
	

	protected SocketHelper(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	public void socketInfo(Socket socket) {
	
		System.out.println("in socketInfo..............");
		System.out.println("threadID:" + Thread.currentThread().getId());
		System.out
				.println("socket.getInetAddress():" + socket.getInetAddress());
		System.out.println("socket.getLocalPort():" + socket.getLocalPort());
		System.out.println("socket.getPort():" + socket.getPort());
		System.out.println("socket.getRemoteSocketAddress():"
				+ socket.getRemoteSocketAddress());

		if (socket.getRemoteSocketAddress() != null) {
			hash.put(Thread.currentThread().getId(), socket
					.getRemoteSocketAddress().toString());

		}

		int remotePort = socket.getLocalPort();
		if (remotePort != -1) {
			String addr = socket.getLocalSocketAddress().toString();
			System.out.println("getLocalSocketAddress:" + addr);

		}
	}

	public void inputStreamSort(InputStream is, Integer i) {
		long threadID = Thread.currentThread().getId();
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(is.toString());
		System.out.println("threadID:" + threadID);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("threadID:" + threadID);

		if (is.toString().contains("SocketInputStream")) {
			System.out.println("i.doubleValue():" + i.doubleValue());
			System.out.println("i.SIZE:" + i.SIZE);

			String hashData = (String) hash.get(Thread.currentThread().getId());
			/*
			 * if(hashData.equals("null")){ hashData="SERVER"; }
			 */
			StringBuffer bf = new StringBuffer();
			bf.append(hashData);
			hashData = bf.deleteCharAt(0).toString();
			hashData = bf.toString().replace(".", "-");
			hashData = hashData.replace(":", ".");
			System.out.println("hashData:" + hashData);
			if (hashData.equals("ull")) {
				hashData="SERVER";
				client.gauge(hashData,i.doubleValue());
			} else {
				System.out.println("if..ull step....1");
				client.gauge(hashData, i.doubleValue());
				System.out.println("if..ull step....2");
			}

		}
	}

	public void serverSocketInfo(ServerSocket serverSocket) {
		if (serverSocket.getLocalSocketAddress() != null) {
			String temp = serverSocket.getLocalSocketAddress().toString();
			System.out.println("Serversocket Info:" + temp);
			System.out.println("serverSocket.getInetAddress().toString():"
					+ serverSocket.getInetAddress().toString());
			if (serverSocket.getChannel() != null) {
				System.out.println("serverSocket.getChannel().toString():"
						+ serverSocket.getChannel().toString());
			}
			System.out.println("serverSocket.getClass().toString():"
					+ serverSocket.getClass().toString());

			try {
				int i = serverSocket.getReceiveBufferSize();
				System.out.println("server i:" + i);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

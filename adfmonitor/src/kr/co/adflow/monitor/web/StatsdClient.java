package kr.co.adflow.monitor.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;

import org.apache.log4j.Logger;

public class StatsdClient {

	protected static PropertiesHostport properties = null;
	protected static String host = null;
	protected static int port = 0;
	private DatagramSocket clientSocket=null;
	private InetAddress IPAddress=null;
	private static Logger logger = Logger.getLogger(StatsdClient.class);

	private static StatsdClient StatsdClinetInstance = new StatsdClient();

	private StatsdClient() {

		init();
	}

	public static StatsdClient getChanInstance() {

		return StatsdClinetInstance;
	}

	public void init() {

		properties = new PropertiesHostport();
		host = properties.read("host");
		String portTemp = properties.read("port");
		port = Integer.parseInt(portTemp);
		logger.info("propertiesHost::" + host);
		logger.info("propertiesPort::" + port);

		if (clientSocket == null) {
			try {
				clientSocket = new DatagramSocket();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			IPAddress=InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void timing(String key, int value) {

		String sentence = String.format(Locale.ENGLISH, "%s:%d|ms", key, value);
		byte[] sendData = new byte[1024];
		try {
			sendData = sentence.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, IPAddress, port);
		this.send(sendPacket);
	}

	public void gauge(String key, double magnitude) {
		String sentence = String.format(Locale.ENGLISH, "%s:%s|g", key,
				magnitude);
		byte[] sendData = new byte[1024];
		try {
			sendData = sentence.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, IPAddress, port);
		this.send(sendPacket);
	}

	private void send(DatagramPacket sendPacket) {

		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		clientSocket.close();
	}

}

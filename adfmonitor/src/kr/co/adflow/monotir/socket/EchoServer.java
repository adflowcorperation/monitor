package kr.co.adflow.monotir.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class EchoServer {
	private static Logger logger = Logger.getLogger(EchoServer.class);

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(10007);
		} catch (IOException e) {

			System.exit(1);
		}

		Socket clientSocket = null;
		System.out.println("Waiting for connection.....");

		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

		System.out.println("Connection successful");
		System.out.println("Waiting for input.....");

		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream(),"UTF-8"));

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			System.out.println("Server: " + inputLine);
			out.println(inputLine);

			if (inputLine.equals("Bye."))
				break;
		}

		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}

}

package kr.co.adflow.monotir.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class EchoClient {

	private static Logger logger = Logger.getLogger(EchoClient.class);

	public static void main(String[] args) throws IOException {

		String serverHostname = new String("127.0.0.1");

		if (args.length > 0)
			serverHostname = args[0];

		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			// echoSocket = new Socket("taranis", 7);
			echoSocket = new Socket(serverHostname, 10007);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream(),"UTF-8"));
		} catch (UnknownHostException e) {

			System.exit(1);
		} catch (IOException e) {

			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in,"UTF-8"));
		String userInput;

		System.out.print("input: ");
		while ((userInput = stdIn.readLine()) != null) {
			out.println(userInput);
			System.out.println("echo: " + in.readLine());
			System.out.print("input: ");
		}

		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
	}

}

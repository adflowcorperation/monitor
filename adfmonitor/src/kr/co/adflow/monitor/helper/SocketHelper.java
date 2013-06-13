package kr.co.adflow.monitor.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class SocketHelper extends Helper {
	

	protected SocketHelper(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	public void socketRead(BufferedReader br) {
		
	
		System.out.println(br.toString());
		
	}

	public void socketReadLine(String str){
		

		byte [] bytearr=str.getBytes();
		System.out.println("byte Size:"+bytearr.length);
	}
	
	public void inputStream(InputStream is){
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Integer i = null;
		try {
			i = is.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("i:"+i);
		System.out.println("i.byteValue():"+i.byteValue());
		System.out.println();
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
	
}
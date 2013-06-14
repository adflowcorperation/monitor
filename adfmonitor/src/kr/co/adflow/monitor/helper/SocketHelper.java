package kr.co.adflow.monitor.helper;

import java.io.InputStream;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class SocketHelper extends Helper {

	protected SocketHelper(Rule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	public void inputStreamSort(InputStream is,Integer i) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(is.toString());
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (is.toString().contains("SocketInputStream")) {
			System.out.println("is...SocketInputStream");
			System.out.println("i.byteValue():"+i.byteValue());
			
		}
	}

}
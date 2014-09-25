package com.xino.zyt.wifidemo.util;

import java.util.Random;


public class ProducePWD {
		
	public static String getRandomPWD(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   

	
}

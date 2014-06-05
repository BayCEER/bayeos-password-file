package de.unibayreuth.bayeos.connection;


import java.security.InvalidParameterException;

import org.apache.commons.codec.binary.Base64;





public class SimpleEncrypter {
		
	private static String xor(String key, String value) throws InvalidParameterException {
		assert(value.length()<=key.length());												
		char[] b = new char[value.length()];		
		for(int i=0;i<value.length();i++){			
			b[i] = (char) (value.charAt(i) ^ key.charAt(i));			
		}													
		return new String(b);
	}
	
	
	public static String encrypt(String key, String value){
		String e = xor(key,value);		
		return new String(Base64.encodeBase64(e.getBytes()));	
	}
	
	
	public static String decrypt(String key, String value){
		String b = new String(Base64.decodeBase64(value.getBytes()));
		return xor(key, b);				
	}
	
	
	

}

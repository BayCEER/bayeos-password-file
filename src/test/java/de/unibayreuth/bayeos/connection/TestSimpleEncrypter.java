package de.unibayreuth.bayeos.connection;



import junit.framework.TestCase;

public class TestSimpleEncrypter extends TestCase {
	
	
	public void testEncrypt(){       
		String key = "1234567890";
		String value = "Paßwörd";				
		assertEquals(value, SimpleEncrypter.decrypt(key,SimpleEncrypter.encrypt(key, value)));		
	}
	
	
}

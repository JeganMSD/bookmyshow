package com.jrorg.bookmyshow.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import com.thoughtworks.xstream.core.util.Base64Encoder;

public class RSAEncryption {
	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	static {
		String modulusString = "2rRVVVFJRbH/wAPDtnwZwu+nxU+AZ6uXxh/sW+AMCBogg7vndZsnRiHoLttYYPqOyOhfgaBOQogrIfrKL4lipK4m52SBzw/FfcM9DsKs/rYR83tBLiIAfgdnVjF27tZID+HJMFTiI30mALjr7+tfp+2lIACXA1RIKTk7S9pDmX8=";
		String publicExponentString = "AQAB";
				

        byte[] modulebytes = Base64.getDecoder().decode(modulusString);
        byte[] exponentbytes = Base64.getDecoder().decode(publicExponentString);
		// Load the key into BigIntegers
		BigInteger modulus = new BigInteger(1, modulebytes);
		BigInteger pubExponent = new BigInteger(1,exponentbytes);
				
		// Create private and public key specs
		RSAPrivateKeySpec privateSpec = new RSAPrivateKeySpec(modulus, pubExponent);
		RSAPublicKeySpec publicSpec = new RSAPublicKeySpec(modulus, pubExponent);
		KeyFactory factory = null;
		try {
			factory = KeyFactory.getInstance("RSA");
	        privateKey = factory.generatePrivate(privateSpec);
			publicKey = factory.generatePublic(publicSpec);
		}
		catch(Exception e) {
			
		}
	}
    public static String encrypt (String plainText) throws Exception
    {
    	try {
	        
    		Cipher cipher = Cipher.getInstance("RSA");
	        
	        //Initialize Cipher for ENCRYPT_MODE
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        byte[] secretMessageBytes = plainText.getBytes(StandardCharsets.UTF_8);
	        //Perform Encryption
	        byte[] cipherText = cipher.doFinal(secretMessageBytes) ;
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
		       
	        byte[] decryptedTextArray = cipher.doFinal(cipherText);
	        
	        return new Base64Encoder().encode(cipherText).replaceAll("\\s", "");
    	}
    	catch(Exception e) {
    		return null;
    	}
    }
    
    public static String decrypt (String text) throws Exception
    {
    	try {
	    	byte[] cipherTextArray = new Base64Encoder().decode(text);
		    Cipher cipher = Cipher.getInstance("RSA");
	        
	        //Initialize Cipher for DECRYPT_MODE
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        
	        //Perform Decryption
	        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);
	        
	        return new String(decryptedTextArray);
    	}
    	catch(Exception e) {
    		return null;
    	}
    }
}

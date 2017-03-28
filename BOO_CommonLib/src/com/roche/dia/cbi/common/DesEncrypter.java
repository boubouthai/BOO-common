package com.roche.dia.cbi.common;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class DesEncrypter {
    Cipher ecipher;
    Cipher dcipher;

    // 8-byte Salt
    byte[] salt = {
        (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
        (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
    };

    // Iteration count
    int iterationCount = 19;

    public DesEncrypter() {
    	this(null);
    }
    public DesEncrypter(String _passPhrase) {
        try {
        	String passPhrase="In, Rantanplan We Trust!";
        	
        	if (_passPhrase!=null)
        		passPhrase=_passPhrase;
            // Create the key
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance(
                "PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (java.security.InvalidAlgorithmParameterException e) {
        } catch (java.security.spec.InvalidKeySpecException e) {
        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) {
        }
    }

    @SuppressWarnings("restriction")
	public String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    @SuppressWarnings("restriction")
	public String decrypt(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }


// Here is an example that uses the class

	public static void main(String[] args) throws Exception 
	{
		 try {
		        // Create encrypter/decrypter class
//		        DesEncrypter encrypter = new DesEncrypter("This is the key passphrase to be used"); 
		        DesEncrypter encrypter = new DesEncrypter(); 
		    
		        System.out.println("encrypting>");
		        // Encrypt
		        String encrypted = encrypter.encrypt("oooo");
		        System.out.println("encrypted:" +  encrypted);
		    
		        System.out.println("decrypting>");
		        // Decrypt
		        String decrypted = encrypter.decrypt("ThisIsMyEncryptPassword");
		        System.out.println("decrypted:" +  decrypted);
		    } catch (Exception e) {
		    }


	}
}

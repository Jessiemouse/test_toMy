package com.example.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@SpringBootTest
class TestApplicationTests {

	@Test
	void contextLoads() throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException {

		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey secretKey = keyGenerator.generateKey();
		String secretKeyString =
				Base64.getEncoder().encodeToString(secretKey.getEncoded());
		System.out.println("generated key = "+secretKeyString);

		//Encrypt Hello world message
		Cipher encryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec parameterSpec = new IvParameterSpec(new byte[16]);
		encryptionCipher.init(Cipher.ENCRYPT_MODE,secretKey,parameterSpec);
		String message = "Hello world";
		byte[] encryptedMessageBytes =
				encryptionCipher.doFinal(message.getBytes());
		String encryptedMessage =
				Base64.getEncoder().encodeToString(encryptedMessageBytes);
		System.out.println("Encrypted message = "+encryptedMessage);
	}

}

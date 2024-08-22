package com.outfit_share.util;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
	public static String encrypt(final String key, final String data) {
		try {
			// Create a new Mac instance with HMAC-SHA256 algorithm
			Mac mac = Mac.getInstance("HmacSHA256");

			// Initialize the Mac with the given key
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
			mac.init(secretKeySpec);

			// Compute the HMAC-SHA256 for the given data
			byte[] hmacData = mac.doFinal(data.getBytes());

			// Convert the byte array to a Base64 string
			return Base64.getEncoder().encodeToString(hmacData);
		} catch (Exception e) {
			throw new RuntimeException("Failed to calculate HMAC-SHA256", e);
		}
	}
}


package com.pedroluizforlan.pontodoc.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class TokenUtils {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateToken() {
        byte[] randomBytes = new byte[32]; // 256 bits
        secureRandom.nextBytes(randomBytes);

        return base64Encoder.encodeToString(randomBytes);
    }

    public static String encrypt(String token) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(token.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean validateToken(String tokenRecebido, String hashSalvo) throws Exception {
        String hashDoRecebido = encrypt(tokenRecebido);
        return hashDoRecebido.equals(hashSalvo);
    }
}

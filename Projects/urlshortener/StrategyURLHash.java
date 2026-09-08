package Projects.urlshortener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StrategyURLHash implements StrategyURL {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String generateKey(String originalUrl) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest((originalUrl + System.nanoTime()).getBytes(StandardCharsets.UTF_8));
            return toBase62(hash).substring(0, 7);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not found", e);
        }
    }

    private String toBase62(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int val = Math.abs(b % 62);
            sb.append(BASE62.charAt(val));
        }
        return sb.toString();
    }
}

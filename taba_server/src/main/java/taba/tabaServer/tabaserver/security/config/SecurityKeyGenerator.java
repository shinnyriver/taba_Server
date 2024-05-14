package taba.tabaServer.tabaserver.security.config;

import java.security.SecureRandom;
import java.util.Base64;

public class SecurityKeyGenerator {
    public static String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256 비트 키 생성
        random.nextBytes(keyBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
    }
}



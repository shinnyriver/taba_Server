package taba.tabaServer.tabaserver.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import taba.tabaServer.tabaserver.security.config.SecurityKeyGenerator;

import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey = SecurityKeyGenerator.generateKey(); // 비밀키 설정

    // JWT 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 유효
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    // JWT에서 클레임 추출
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    // 특정 클레임, 예를 들어 username 추출
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰 만료 시간 확인
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token, String username) {
        final String currentUsername = extractUsername(token);
        return (username.equals(currentUsername) && !isTokenExpired(token));
    }
}

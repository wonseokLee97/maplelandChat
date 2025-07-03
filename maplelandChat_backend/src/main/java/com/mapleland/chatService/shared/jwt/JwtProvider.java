package com.mapleland.chatService.shared.jwt;

import com.mapleland.chatService.shared.utils.IpAddressUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class JwtProvider {
    private final SecretKey key;
    private final String serverIdentifier;
    private final long expirationSeconds;
    private final long refreshExpirationSeconds;
    private final JwtParser jwtParser;

    public JwtProvider(
            @Value("${token.secret}") String secret,
            @Value("${token.server-ip}") String serverAddress,
            @Value("${token.token-expiration}") long expirationSeconds,
            @Value("${token.token-refresh-expiration}") long refreshExpirationSeconds
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.refreshExpirationSeconds = refreshExpirationSeconds;
        this.serverIdentifier = generateServerIdentifier(serverAddress, secret);
        this.expirationSeconds = expirationSeconds;
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    public String generateAccessToken(String userName, String imgPath, HttpServletRequest request) throws UnknownHostException {
        Instant now = Instant.now();

        if (userName == null && imgPath == null) {
            List<String> profileList = generateRandomName();
            userName = profileList.get(0);
            imgPath = profileList.get(1);
        }

        String ipAddress = IpAddressUtil.extractIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String UUID = Base64.getUrlEncoder().withoutPadding().encodeToString((userAgent + ipAddress).getBytes());

        // UUID = userName + IpAddress + ..

        return Jwts.builder()
                .id(UUID)
                .issuer(serverIdentifier)
                .claim("userName", userName)
                .claim("imgPath", imgPath)
                .audience().add("mapleland").and()
                .issuedAt(Date.from(Instant.ofEpochSecond(now.getEpochSecond())))
                .notBefore(Date.from(Instant.ofEpochSecond(now.minusSeconds(600).getEpochSecond())))
                .expiration(Date.from(Instant.ofEpochSecond(now.plusSeconds(expirationSeconds).getEpochSecond())))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String userName, String imgPath, HttpServletRequest request) throws UnknownHostException {
        Instant now = Instant.now();

        if (userName == null && imgPath == null) {
            List<String> profileList = generateRandomName();
            userName = profileList.get(0);
            imgPath = profileList.get(1);
        }

        String ipAddress = IpAddressUtil.extractIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String UUID = Base64.getUrlEncoder().withoutPadding().encodeToString((userAgent + ipAddress).getBytes());

        return Jwts.builder()
                .id(UUID)
                .issuer(serverIdentifier)
                .claim("userName", userName)
                .claim("imgPath", imgPath)
                .audience().add("mapleland").and()
                .issuedAt(Date.from(Instant.ofEpochSecond(now.getEpochSecond())))
                .notBefore(Date.from(Instant.ofEpochSecond(now.minusSeconds(600).getEpochSecond())))
                .expiration(Date.from(Instant.ofEpochSecond(now.plusSeconds(refreshExpirationSeconds).getEpochSecond())))
                .signWith(key)
                .compact();
    }


    public static List<String> generateRandomName() {
        // 랜덤 몬스터 선택
        MonsterName[] monsters = MonsterName.values();
        MonsterName randomMonster = monsters[ThreadLocalRandom.current().nextInt(monsters.length)];

        // 1~50 사이 랜덤 숫자 생성
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 101);

        List<String> list = new ArrayList<>();
        list.add(randomMonster.getKoreanName() + randomNumber);
        list.add(randomMonster.getPath());

        return list;
    }


    public String generateNameTag() {
        // UUID 를 생성하고, 이를 16진수로 변환 후, 대문자로 변경
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        // UUID 에서 필요한 길이만큼 자르고, #을 앞에 붙여서 nameTag 로 생성
        return "#" + uuid.substring(0, 6); // 예: #YQTQJG
    }


    public TokenClaims validateToken(String token) {
        try {
            Claims claims = jwtParser
                    .parseSignedClaims(token)
                    .getPayload();

            // Validate issuer and audience
            if (!claims.getIssuer().equals(serverIdentifier)) {
                throw new JwtException.TokenException("Invalid token claims!!");
            }

            return TokenClaims.builder()
                    .id(claims.getId())
                    .issuer(claims.getIssuer())
                    .userName(claims.get("userName", String.class))
                    .imgPath(claims.get("imgPath", String.class))
                    .issuedAt(claims.getIssuedAt().toInstant().getEpochSecond())
                    .notBefore(claims.getNotBefore().toInstant().getEpochSecond())
                    .expiresAt(claims.getExpiration().toInstant().getEpochSecond())
                    .build();

        } catch (ExpiredJwtException e) {
            throw new JwtException.ExpiredJwtException("Token Expired");
        } catch (SecurityException | UnsupportedJwtException | BadCredentialsException e) {
            throw new JwtException.TokenException("Invalid JWT token");
        } catch (IllegalArgumentException e) {
            throw new JwtException.IllegalArgumentException("Token cannot be null or empty");
        } catch (MalformedJwtException e) {
            throw new JwtException.MalformedJwtException("Compact JWT strings may not contain whitespace.");
        }
    }

    private String generateServerIdentifier(String serverAddress, String secret) {
        return UUID.nameUUIDFromBytes(
                (serverAddress + secret).getBytes(StandardCharsets.UTF_8)
        ).toString();
    }
}

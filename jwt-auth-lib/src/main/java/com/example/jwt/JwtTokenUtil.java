package com.example.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Optional;

public class JwtTokenUtil {
    public static Optional<DecodedJWT> decodeToken(String token) {
        try {
            return Optional.of(JWT.decode(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static String getUserEmail(DecodedJWT jwt) {
        return jwt.getClaim("email").asString();
    }

    public static String getRole(DecodedJWT jwt) {
        return jwt.getClaim("role").asString();
    }

    public static boolean isTokenExpired(DecodedJWT jwt) {
        return jwt.getExpiresAt().before(new java.util.Date());
    }
}

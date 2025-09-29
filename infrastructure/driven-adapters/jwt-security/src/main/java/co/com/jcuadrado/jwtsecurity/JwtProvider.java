package co.com.jcuadrado.jwtsecurity;

import co.com.jcuadrado.jwtsecurity.constant.LogMessages;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    private SecretKey getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return true;
        } catch (ExpiredJwtException e) {
            log.error(LogMessages.TOKEN_EXPIRED_LOG, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(LogMessages.TOKEN_UNSUPPORTED_LOG, e.getMessage());
        } catch (MalformedJwtException e) {
            log.error(LogMessages.TOKEN_MALFORMED_LOG, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(LogMessages.ILLEGAL_ARGS_LOG, e.getMessage());
        } catch (Exception e) {
            log.error(LogMessages.TOKEN_SIGNATURE_ERROR_LOG, e.getMessage());
        }
        return false;
    }

}

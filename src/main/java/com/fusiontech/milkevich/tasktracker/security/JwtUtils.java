package com.fusiontech.milkevich.tasktracker.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Jwt Utils.
 *
 * @author Milkevich Egor
 */
@Component
@Log4j2
public class JwtUtils {

  @Value("${tasks.app.jwtSecret}")
  private String jwtSecret;

  @Value("${tasks.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  /**
   * Generates a JWT token based on the provided authentication.
   *
   * @param authentication the authentication object containing the user's details
   * @return the generated JWT token
   */
  public String generateJwtToken(Authentication authentication) {

    String userPrincipal = authentication.getPrincipal().toString();

    JwtBuilder jwtBuilder = Jwts.builder()
        .setSubject(userPrincipal)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256);

    return jwtBuilder.compact();
  }

  /**
   * Generates the key for the function.
   *
   * @return The generated key for the function.
   */
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  /**
   * Retrieves the username from a JWT token.
   *
   * @param token the JWT token
   * @return the username extracted from the token
   */
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  /**
   * Validates a JWT token.
   *
   * @param authToken the JWT token to validate
   * @return true if the token is valid, false otherwise
   */
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}

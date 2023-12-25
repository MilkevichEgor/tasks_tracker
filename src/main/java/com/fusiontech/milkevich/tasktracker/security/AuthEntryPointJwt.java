package com.fusiontech.milkevich.tasktracker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * This class  serves as the entry point for unauthorized requests.
 * It handles the  of the authentication process and returns an unauthorized response.
 */
@Component
@Log4j2
public class AuthEntryPointJwt implements AuthenticationEntryPoint {


  /**
   * Commences the authentication process when an authentication exception occurs.
   *
   * @param request       the HTTP servlet request
   * @param response      the HTTP servlet response
   * @param authException the authentication exception
   * @throws IOException      if an I/O error occurs
   * @throws ServletException if a servlet error occurs
   */
  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException)
      throws IOException, ServletException {

    log.error("Unauthorized error: {}", authException.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    final Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", authException.getMessage());
    body.put("path", request.getServletPath());

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), body);
  }
}

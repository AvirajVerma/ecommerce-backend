package com.auth.jwt.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        //
//        response.setContentType("application/json");
//        Map<String, Object> data = new HashMap<>();
//        data.put("timestamp", LocalDateTime.now().toString());
//        data.put("status", 401);
//        data.put("error", "Unauthorized");
//        data.put("message", authException.getMessage());
//        data.put("path", request.getRequestURI());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", LocalDateTime.now().toString());
        data.put("status", 403);
        data.put("error", "Forbidden");
        data.put("message", accessDeniedException.getMessage());
        data.put("path", request.getRequestURI());

        ObjectMapper objectMapper = new ObjectMapper();
        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}

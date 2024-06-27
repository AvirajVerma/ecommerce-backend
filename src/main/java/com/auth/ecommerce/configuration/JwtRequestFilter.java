package com.auth.ecommerce.configuration;


import com.auth.ecommerce.service.JwtService;
import com.auth.ecommerce.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static String CURRENT_USER = "";

    @Autowired
    private JwtUtil jwtUtil;

    private final JwtService jwtService;

    @Autowired
    public JwtRequestFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        String jwtToken = null;
        String userName = null;
        if(header != null && header.startsWith("Bearer ")){
            jwtToken = header.substring(7);

            try {
                userName = jwtUtil.getUserNameFromToken(jwtToken);
                CURRENT_USER = userName;

            } catch (IllegalArgumentException e){
                System.out.println("Unable to get JWT Token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT token is expired");
            }
        }else {
            System.out.println("JWT token does not start with bearer");
        }

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails =  jwtService.loadUserByUsername(userName);

            if(jwtUtil.validateToken(jwtToken, userDetails)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

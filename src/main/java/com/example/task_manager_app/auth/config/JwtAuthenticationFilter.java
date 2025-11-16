package com.example.task_manager_app.auth.config;

import com.example.task_manager_app.auth.interfaces.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String ROLE_CLAIM = "role";

    private final JwtService jwtService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(AUTHORIZATION);

        if (Objects.isNull(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        Optional<UsernamePasswordAuthenticationToken> authentication = getAuthentication(req);
        authentication.ifPresentOrElse(e -> SecurityContextHolder.getContext().setAuthentication(e), SecurityContextHolder::clearContext);

        chain.doFilter(req, res);
    }

    private Optional<UsernamePasswordAuthenticationToken> getAuthentication(
            HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (Objects.nonNull(token)) {
            try {
                Claims claims = jwtService.parseToken(token.replace(TOKEN_PREFIX, ""));
                String subject = claims.getSubject();

                String authority = claims.get(ROLE_CLAIM, String.class);

                if (Objects.nonNull(subject)) {
                    return Optional.of(new UsernamePasswordAuthenticationToken(subject, null, List.of(new SimpleGrantedAuthority(authority))));
                }

            } catch (Exception e) {
                // invalid token
            }
        }
        return Optional.empty();
    }
}
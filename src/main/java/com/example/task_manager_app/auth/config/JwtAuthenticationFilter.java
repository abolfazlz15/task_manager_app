package com.example.task_manager_app.auth.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.task_manager_app.common.constant.IConstant;
import com.example.task_manager_app.common.exception.UnauthorizedException;
import com.example.task_manager_app.user.enums.PrivilegeEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import com.example.task_manager_app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.auth.interfaces.JwtService;
import com.example.task_manager_app.auth.repository.RoleRepository;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String ROLE_CLAIM = "authorities";

    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, RoleRepository roleRepository, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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

                if(!userRepository.existsByIdAndStatusIs(Long.parseLong(subject), UserStatusEnum.ACTIVE)) {
                    throw new UnauthorizedException("Invalid user");
                }

                List<Long> authorities = claims.get(ROLE_CLAIM, List.class);
                List<RoleEntity> roles = roleRepository.findAllById(authorities);
                List<SimpleGrantedAuthority> granted = new ArrayList<>();

                if (userRepository.existsByIdAndPrivilegeIs(Long.parseLong(subject), PrivilegeEnum.SUPER_ADMIN)) {
                    granted.add(new SimpleGrantedAuthority(PrivilegeEnum.SUPER_ADMIN.toString()));
                }

                if (Objects.nonNull(subject)) {
                    roles.forEach(r -> {
                        r.getPermissions().forEach(p -> {
                            granted.add(new SimpleGrantedAuthority(p.name()));
                        });
                    });
                    return Optional.of(new UsernamePasswordAuthenticationToken(subject, null, granted));
                }


            } catch (Exception e) {
                // invalid token
            }
        }
        return Optional.empty();
    }
}
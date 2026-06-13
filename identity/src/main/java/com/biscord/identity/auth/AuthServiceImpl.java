package com.biscord.identity.auth;

import com.biscord.identity.auth.dtos.AuthResponse;
import com.biscord.identity.auth.dtos.LoginRequest;
import com.biscord.identity.auth.dtos.RegisterRequest;
import com.biscord.identity.user.User;
import com.biscord.identity.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }
        if (userService.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .profileUrl(null)
                .uuid(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();
        userService.save(user);

        return new AuthResponse(request.username(), jwtUtil.generateToken(request.username()));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String token = jwtUtil.generateToken(username);

        return new AuthResponse(username, token);
    }
}

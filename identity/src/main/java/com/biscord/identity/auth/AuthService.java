package com.biscord.identity.auth;


import com.biscord.identity.auth.dtos.AuthResponse;
import com.biscord.identity.auth.dtos.LoginRequest;
import com.biscord.identity.auth.dtos.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}

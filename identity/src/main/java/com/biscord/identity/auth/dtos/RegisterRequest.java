package com.biscord.identity.auth.dtos;

public record RegisterRequest(String username, String email, String password) {
}

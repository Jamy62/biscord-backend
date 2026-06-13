package com.biscord.identity.user;

public interface UserService {
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    String getUsernameByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void save(User user);
}

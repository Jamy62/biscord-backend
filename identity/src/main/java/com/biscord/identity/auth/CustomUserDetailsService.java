package com.biscord.identity.auth;

import com.biscord.common.exception.customExceptions.ResourceNotFoundException;
import com.biscord.identity.user.User;
import com.biscord.identity.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user;

            if (username.contains("@")) {
                user = userService.getUserByEmail(username);
            }
            else {
                user = userService.getUserByUsername(username);
            }

            return new CustomUserDetails(user);
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(username.contains("@") ? "Invalid email or password" : "Invalid username or password");
        }
    }
}
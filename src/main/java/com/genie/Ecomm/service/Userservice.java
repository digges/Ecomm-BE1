// Userservice.java - REFINED VERSION
package com.genie.Ecomm.service;

import com.genie.Ecomm.model.User;
import com.genie.Ecomm.repo.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class Userservice implements UserDetailsService {

    private final UserRepository userrepo;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection instead of @Autowired
    public Userservice(UserRepository userrepo, PasswordEncoder passwordEncoder) {
        this.userrepo = userrepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================================
    // SPRING SECURITY - Used for login
    // ============================================
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userrepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = userOptional.get();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    // ============================================
    // YOUR BUSINESS METHODS
    // ============================================

    // Register new user with encrypted password
    public User registerUser(User user) {
        // Check if email already exists
        Optional<User> existingUser = userrepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userrepo.save(user);
    }

    // Find user by email
    public User findByEmail(String email) {
        Optional<User> user = userrepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }
        return user.get();
    }

    // Get all users
    public List<User> getAllUsers() {
        return userrepo.findAll();
    }

    // Verify password
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Update user
    public User updateUser(User user) {
        return userrepo.save(user);
    }

    // Delete user
    public void deleteUser(Long userId) {
        userrepo.deleteById(userId);
    }

    // Get user by ID
    public User getUserById(Long userId) {
        return userrepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
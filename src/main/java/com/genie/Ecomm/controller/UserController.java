package com.genie.Ecomm.controller;

import com.genie.Ecomm.model.User;
import com.genie.Ecomm.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class UserController {

    @Autowired
    private Userservice userservice;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Encode password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userservice.registerUser(user);

            response.put("success", true);
            response.put("message", "Registration successful!");
            response.put("user", savedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            User user = userservice.findByEmail(email);

            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("user", Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "name", user.getName() != null ? user.getName() : ""
                ));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userservice.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userservice.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
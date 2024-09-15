package com.javaproject.authanticator.controllers;

import com.javaproject.authanticator.dto.ResponseDTO;
import com.javaproject.authanticator.model.User;
import com.javaproject.authanticator.repository.UserRepository;
import com.javaproject.authanticator.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok(userRepository.findAll().toString());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> editUser(@PathVariable String id, @RequestBody User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
                existingUser.setPassword(encodedPassword);
            }

            userRepository.save(existingUser);

            String token = tokenService.generateToken(existingUser);
            return ResponseEntity.ok(new ResponseDTO(existingUser.getName(), token));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

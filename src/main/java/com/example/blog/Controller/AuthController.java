package com.example.blog.Controller;

import com.example.blog.Entity.User;
import com.example.blog.Repository.UserRepository;
import com.example.blog.Security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    AuthController(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.jwtUtil=jwtUtil;
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody User loginRequest){
        User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }
        String token =jwtUtil.generateToken(loginRequest.getEmail());
        return ResponseEntity.ok(Map.of("token ",token));
    }
}

package com.auth.example.controllers;

import com.auth.example.domain.User;
import com.auth.example.dto.AuthenticationDTO;
import com.auth.example.dto.LoginResponseDTO;
import com.auth.example.dto.RegisterDTO;
import com.auth.example.repositories.UserRepository;
import com.auth.example.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data){
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDTO registerDTO){
        if(Objects.nonNull(this.userRepository.findByLogin(registerDTO.login()))){
            return ResponseEntity.badRequest().build();
        }

        String ecryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.login(), ecryptedPassword, registerDTO.role());
        userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}

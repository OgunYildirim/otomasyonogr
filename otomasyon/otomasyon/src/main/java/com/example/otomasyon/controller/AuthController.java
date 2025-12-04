package com.example.otomasyon.controller;

import com.example.otomasyon.entity.User;
import com.example.otomasyon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return "Giriş başarılı! Rol: " + user.getRole();
        } else {
            return "Kullanıcı adı veya şifre yanlış!";
        }
    }
}


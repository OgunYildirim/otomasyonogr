package com.example.otomasyon;

import com.example.otomasyon.entity.User;
import com.example.otomasyon.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicApiTests {
    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Test
    void userCrudTest() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("1234");
        user.setRole("admin");
        User saved = userRepository.save(user);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("testuser", saved.getUsername());

        User found = userRepository.findById(saved.getId()).orElse(null);
        Assertions.assertNotNull(found);
        Assertions.assertEquals("admin", found.getRole());

        userRepository.deleteById(saved.getId());
        Assertions.assertNull(userRepository.findById(saved.getId()).orElse(null));
    }

    @Test
    void loginApiTest() {
        User user = new User();
        user.setUsername("apiuser");
        user.setPassword("pass");
        user.setRole("hoca");
        userRepository.save(user);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/auth/login?username=apiuser&password=pass";
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        Assertions.assertTrue(response.getBody().contains("Giriş başarılı"));
    }
}


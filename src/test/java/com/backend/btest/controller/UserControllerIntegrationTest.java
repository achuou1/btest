package com.backend.btest.controller;

import com.backend.btest.entity.User;
import com.backend.btest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllUsers_shouldReturnOk() {
        userRepository.save(createUser(1L, "Peter"));
        userRepository.save(createUser(2L, "Mattson"));

        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().length, 2);
    }

    private User createUser(Long userId, String name) {
        return User.builder().id(userId).name(name).build();
    }
}
package com.example.restservice.service;

import com.example.restservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
public class BpdtsService {

    Logger logger = LoggerFactory.getLogger(BpdtsService.class);

    WebClient client = WebClient.create("https://bpdts-test-app.herokuapp.com");

    public List<User> findAll() {
        User[] userArr = client
                .get().uri("/users")
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    WebClientException we = clientResponse.createException().block();
                    throw new ResponseStatusException(clientResponse.statusCode(), we.getMessage(), we);
                }).bodyToMono(User[].class).block();

        return Arrays.asList(userArr);
    }

    public User findById(Long id) {
        User user = null;
        ClientResponse clientResponse = client.get().uri("/user/" + id).exchange().block();

        if (clientResponse.statusCode().is2xxSuccessful()) {
            user = clientResponse.bodyToMono(User.class).block();
        } else if (clientResponse.statusCode().isError()) {
            WebClientException we = clientResponse.createException().block();
            throw new ResponseStatusException(clientResponse.statusCode(), we.getMessage(), we);
        }
        return user;
    }

    public List<User> findUsersWithin(String city) {
        User[] userArr = client
                .get().uri("/city/" + city + "/users")
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    WebClientException we = clientResponse.createException().block();
                    throw new ResponseStatusException(clientResponse.statusCode(), we.getMessage(), we);
                }).bodyToMono(User[].class).block();


        return Arrays.asList(userArr);
    }
}


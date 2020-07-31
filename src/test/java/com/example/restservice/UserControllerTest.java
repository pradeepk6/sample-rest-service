package com.example.restservice;

import com.example.restservice.model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@SpringBootTest
public class UserControllerTest {

    Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    WebTestClient webTestClient = WebTestClient
            .bindToServer()
            .baseUrl("http://localhost:8080")
            .responseTimeout(Duration.ofMillis(12000))
            .build();

    @Test
    public void given_userIdIs2_when_getUserById_then_returnFullUserDetails() {

        User expectedUser = new User(
                "2", "Bendix", "Halgarth", "bhalgarth1@timesonline.co.uk",
                "4.185.73.82", -2.9623869, 104.7399789, "Kundung"
        );
        webTestClient
                .get()
                .uri("/user/2")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody(User.class).isEqualTo(expectedUser);
    }

    @Test
    public void given_nonExistingUser_when_getUserById_then_return404() {
        webTestClient
                .get()
                .uri("/user/123456")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void given_idIsString_when_getUserById_then_return400() {
        webTestClient
                .get()
                .uri("/user/qwerty")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(400);
    }

    @Test
    public void when_getAllUsers_then_returnAllUsers() {
        webTestClient
                .get()
                .uri("/users")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1000);

    }

    @Test
    public void given_cityIsLondon_when_getAllUsersLivingInAndAroundCity_then_return() {
        webTestClient
                .get()
                .uri("/city/London/users")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(6);

    }

    @Test
    public void given_cityIsBlank_when_getAllUsersLivingInAndAroundCity_then_return400() {
        webTestClient
                .get()
                .uri("/city/ /users")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isEqualTo(400);
    }
}

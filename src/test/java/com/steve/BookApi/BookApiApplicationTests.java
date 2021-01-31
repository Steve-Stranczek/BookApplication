package com.steve.BookApi;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
class BookApiApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Rule
    public MySQLContainer mysql = new MySQLContainer(DockerImageName.parse("mysql:latest"));

    @BeforeEach
    public void setUp() {
        String address = mysql.getHost();
        Integer port = mysql.getFirstMappedPort();
    }

    @Test
    public void getAllBooks() {
//        this.mockMvc.perform()
    }

    @Test
    public void greetingShouldReturnDefaultMessage() {
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);

    }

}

// TODO
/**
 * https://www.testcontainers.org/quickstart/junit_5_quickstart/
 * https://www.testcontainers.org/modules/databases/jdbc/
 * https://programmerfriend.com/testcontainers-springboot-real-database
 * https://www.baeldung.com/spring-boot-testcontainers-integration-test
 * https://medium.com/javarevisited/integration-tests-with-spring-boot-testcontainers-liquibase-and-junit-5-13fb1ae70b40
 * https://mydeveloperplanet.com/2020/05/05/easy-integration-testing-with-testcontainers/
 * https://spring.io/guides/gs/testing-web/
 **/
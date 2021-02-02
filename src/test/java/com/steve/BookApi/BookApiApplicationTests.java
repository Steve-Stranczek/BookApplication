package com.steve.BookApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.BookApi.model.Author;
import com.steve.BookApi.model.Book;
import com.steve.BookApi.model.Genre;
import com.steve.BookApi.repository.BookRepository;
import com.steve.BookApi.service.BookService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.steve.BookApi.SqlConstants;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static com.steve.BookApi.SqlConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@Sql({"/init_mysql.sql"})
class BookApiApplicationTests {

    @Container
    @Rule
    static MySQLContainer mysql = new MySQLContainer(DockerImageName.parse("mysql:latest"))
            .withDatabaseName("bookdb");

    @DynamicPropertySource
    static void mySqlProperties(DynamicPropertyRegistry registry)
    {
        registry.add("database.book.url", mysql::getJdbcUrl);
        registry.add("database.book.username", mysql::getUsername);
        registry.add("database.book.password", mysql::getPassword);
    }


    @LocalServerPort
    private int port;

    @Autowired
    private BookService bookService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }


    @BeforeEach
    public void setUp() {
        mysql.start();
        String address = mysql.getHost();
        Integer port = mysql.getFirstMappedPort();
    }



    @Test
    public void getAllBooksWhenDbEmptyShouldBeZero() {
        int sizeOfBooks = bookService.getAllBooks().size();
        Assert.assertEquals(0, sizeOfBooks);
    }

    @Test
    public void insertOneBookShouldBe1ForFirstBookId(){
        Book book = new Book();
        Author author = new Author();
        author.name = "Richard Powers";
        Genre genre = new Genre();
        genre.id = 1;
        book.author = author;
        book.genre = genre;
        book.title = "The Overstory";
        book.pages = 502;

        long bookId = bookService.insertBook(book);
        Assert.assertEquals(1, bookId);
    }

    @Test
    public void deleteBookWhichDoesntExistShouldBe0()
    {

    }

   // @Test
   // public void greetingShouldReturnDefaultMessage() {
    //    String response = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);

   // }

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
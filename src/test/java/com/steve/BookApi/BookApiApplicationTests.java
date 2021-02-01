package com.steve.BookApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.BookApi.model.Author;
import com.steve.BookApi.model.Book;
import com.steve.BookApi.model.Genre;
import com.steve.BookApi.repository.BookRepository;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.steve.BookApi.SqlConstants;

import javax.xml.crypto.Data;
import java.util.Collections;

import static com.steve.BookApi.SqlConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
class BookApiApplicationTests {

    @LocalServerPort
    private int port;

    private BookRepository bookRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Rule
    public MySQLContainer mysql = new MySQLContainer(DockerImageName.parse("mysql:latest"))
            .withDatabaseName("bookdb");


    @BeforeEach
    public void setUp() {
        mysql.start();
        String address = mysql.getHost();
        Integer port = mysql.getFirstMappedPort();
        DataSource ds = new DataSource();
        ds.setUrl(mysql.getJdbcUrl());
        ds.setUsername(mysql.getUsername());
        ds.setPassword(mysql.getPassword());
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
        template.update(createAuthorTable,new MapSqlParameterSource());
        template.update(createGenreTable,new MapSqlParameterSource());
        template.update(createBookTable, new MapSqlParameterSource());
        template.update(insertGenres, new MapSqlParameterSource());
        bookRepository = new BookRepository(new ObjectMapper(), template);
    }

    @Test
    public void getAllBooksWhenDbEmptyShouldBeZero() {
        int sizeOfBooks = bookRepository.getAllBooks().size();
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

        long bookId = bookRepository.insertBook(book);
        Assert.assertEquals(1, bookId);
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
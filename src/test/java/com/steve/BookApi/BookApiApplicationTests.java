package com.steve.BookApi;

import com.steve.BookApi.model.Author;
import com.steve.BookApi.model.Book;
import com.steve.BookApi.model.Genre;
import com.steve.BookApi.service.BookService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
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
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllBooksWhenDbEmptyShouldBeZero() {
        int sizeOfBooks = bookService.getAllBooks().size();
        Assert.assertEquals(0, sizeOfBooks);
    }

    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void insertOneBookShouldBe1ForFirstBookId(){
        Book book = getTestBook();

        long bookId = bookService.insertBook(book);
        Assert.assertEquals(1, bookId);
    }

    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteBookWhichDoesntExistShouldBe0()
    {
        long bookId = bookService.deleteBook(1);
        Assert.assertEquals(0,0);
    }

    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void DeleteBookAfterInsertingShouldBe1()
    {
        Book book = getTestBook();

        bookService.insertBook(book);
        long deletedBookId = bookService.deleteBook(1);
        Assert.assertEquals(1,deletedBookId);
    }

    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void InsertBookThatAlreadyExistsShouldBe0() {
        Book book = getTestBook();

        bookService.insertBook(book);
        long bookInsertId = bookService.insertBook(book);
        Assert.assertEquals(0,bookInsertId);
    }

    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void UpdateBookThatDoesntExistShouldBe0() {
        Book book = getTestBook();
        book.id = 1;

        long updateId = bookService.updateBook(book);
        Assert.assertEquals(0,updateId);
    }


    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void UpdateBookThatAlreadyExistsShouldBe1() {
        Book book = getTestBook();

        bookService.insertBook(book);
        book.title = "Test Title";
        book.id = 1;
        long updateId = bookService.updateBook(book);
        Assert.assertEquals(1,updateId);
    }

    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void GetBookWithAuthorWhoDoesntExistShouldBeNull() {
        Book book = new Book();
        book.author = new Author();
        book.genre = new Genre();
        book.author.name = "TEST";

        Book retBook = bookService.getBook(book.title, book.author.name);
        Assert.assertEquals(null, retBook);
    }

    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void GetBookWithATitleThatDoesntExistShouldBeNull() {
        Book book = new Book();
        book.author = new Author();
        book.genre = new Genre();
        book.title = "TEST";

        Book retBook = bookService.getBook(book.title, book.author.name);
        Assert.assertEquals(null, retBook);
    }

    @Test
    @Sql(value = "/init_mysql.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/tearDown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void GetBookWithTitleAndAuthorWhichExistsShouldNotBeNull() {
        Book book = new Book();
        book.author = new Author();
        book.genre = new Genre();
        book.title = "The Overstory";
        book.author.name = "Richard Powers";

        Book insertBook = getTestBook();
        bookService.insertBook(insertBook);

        Book retBook = bookService.getBook(book.title, book.author.name);
        Assert.assertNotEquals(null, retBook);
    }



    private Book getTestBook()
    {
        Book book = new Book();
        Author author = new Author();
        author.name = "Richard Powers";
        Genre genre = new Genre();
        genre.id = 1;
        book.author = author;
        book.genre = genre;
        book.title = "The Overstory";
        book.pages = 502;

        return book;
    }
}

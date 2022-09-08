package bookstoread;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 Is normaly written test classes tha end with Test or Tests, this book follow the behavior-driven-development
 (BDD) naming convention. The *Test naming convention forces you to think that your unit tests are the only
 quality assurance facility here we wanto to think in terms of behavior specification so Tests here will
 end with Spec.

 With @DisplayName annotation you can name a class, method for more legible reading.

 The @Disabled annotation is used for ignoring the execution of failing tests, can be used in class level too.
 If a test is marked with @Disabled annotation then JUnit wouldn't run it. If a class is marked with the @Disabled
 then will skip all tests inside the respective class.
 */


@DisplayName("= BookShelf Specification =")
public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;

    @BeforeEach
    void init() throws Exception {
        shelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
    }

    @Test
    @DisplayName("is empty when no book is added to it")
    public void shelfEmptyWhenNoBookAdded() throws Exception {
        List<Book> books = shelf.books();
        Assertions.assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("must contain two books in BookShelf when add 2 books")
    void bookshelfContainsTwoBooksWhenTwoBooksAreAdd() {
        shelf.add(effectiveJava, codeComplete);
        List<Book> books = shelf.books();
        Assertions.assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    @DisplayName("Must be empty bookshelf when add is called without books")
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        shelf.add();
        List<Book> books = shelf.books();
        Assertions.assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("must clients not be able to modify books for bookshelf")
    public void booksReturnedFromBookShelfIsImmutableForClients() {
        shelf.add(effectiveJava, codeComplete);
        List<Book> books = shelf.books();

        try {
            books.add(mythicalManMonth);
            Assertions.fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException");
        }
    }
    @Disabled("Needs to implement Comparator")
    @Test
    @DisplayName("must books be arranged by book title")
    public void bookShelfArrangedByBookTitle() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.arrange();
        Assertions.assertEquals(Arrays.asList(codeComplete, effectiveJava, mythicalManMonth),
                books, () -> "Books in a bookshelf should be arranged lexicographically by book title");
    }

    @Test
    @DisplayName("must books in bookshelf be in insertion order after calling arrange method")
    public void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        shelf.arrange();
        List<Book> books = shelf.books();
        Assertions.assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth),
                books, () -> "Books in bookshelf are in insertion order");
    }

    @Test
    @DisplayName("must bookshelf be arranged by user provided criteria")
    public void bookshelfArrangedByUserProvidedCriteria() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.arrange(Comparator.<Book>naturalOrder().reversed());
        Assertions.assertEquals(
                Arrays.asList(mythicalManMonth, effectiveJava, codeComplete), books,
                () -> "Books in a bookshelf are arranged in descending order of book title");
    }

    @Test
    @DisplayName("must bookshelf be arranged by publication date")
    public void bookshelfArrangedByPublicationDate() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.arrange(Comparator.<Book>naturalOrder());

    }

}

// TODO Continue in page 59, Download AssertJ package.

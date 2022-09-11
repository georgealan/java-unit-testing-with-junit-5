package bookstoread;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

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


@DisplayName("A BookShelf")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init(Map<String, Book> books) throws Exception {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
    }

    @Nested
    @DisplayName("Is Empty")
    class IsEmpty {
        @Test
        @DisplayName("bookshelf is empty when no book is added to it")
        public void emptyBookShelfWhenNoBookAdded() throws Exception {
            List<Book> books = shelf.books();
            Assertions.assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }

        @Test
        @DisplayName("Must be empty bookshelf when add is called without books")
        public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
            shelf.add();
            List<Book> books = shelf.books();
            Assertions.assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }
    }

    @Nested
    @DisplayName("After adding books")
    class BooksAreAdded {
        @Test
        @DisplayName("must contain two books in BookShelf when add 2 books")
        void bookshelfContainsTwoBooksWhenTwoBooksAreAdd() {
            shelf.add(effectiveJava, codeComplete);
            List<Book> books = shelf.books();
            Assertions.assertEquals(2, books.size(), () -> "BookShelf should have two books.");
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

        @Test
        @DisplayName("must books in bookshelf be in insertion order after calling arrange method")
        public void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            shelf.arrange();
            List<Book> books = shelf.books();
            Assertions.assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth),
                    books, () -> "Books in bookshelf are in insertion order");
        }
    }

    @Nested
    @DisplayName("Is arranged")
    class BooksArranged {
        @Test
        @DisplayName("must bookshelf be arranged by user provided criteria")
        public void bookshelfArrangedByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> books = shelf.arrange(reversed);
            assertThat(books).isSortedAccordingTo(reversed);
        }

        @Test
        @DisplayName("books inside bookshelf are grouped by publication year")
        public void groupBooksInsideBookShelfByPublicationYear() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2008))
                    .containsValues(Arrays.asList(effectiveJava, cleanCode));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2004))
                    .containsValues(Collections.singletonList(codeComplete));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(1975))
                    .containsValues(Collections.singletonList(mythicalManMonth));
        }

        @Test
        @DisplayName("books inside bookshelf are grouped according to user provided criteria(group by author name)")
        public void groupBooksByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);

            assertThat(booksByAuthor)
                    .containsKey("Joshua Bloch")
                    .containsValues(Collections.singletonList(effectiveJava));

            assertThat(booksByAuthor)
                    .containsKey("Steve McConnel")
                    .containsValues(Collections.singletonList(codeComplete));

            assertThat(booksByAuthor)
                    .containsKey("Frederick Phillips Brooks")
                    .containsValues(Collections.singletonList(mythicalManMonth));

            assertThat(booksByAuthor)
                    .containsKey("Robert C. Martin")
                    .containsValues(Collections.singletonList(cleanCode));
        }

        @Test
        @DisplayName("must books be arranged by book title")
        public void bookShelfArrangedByBookTitle() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<String, List<Book>> booksByTitle = shelf.groupBy(Book::getTitle);

            assertThat(booksByTitle)
                    .containsKey("Effective Java")
                    .containsValues(Collections.singletonList(effectiveJava));

            assertThat(booksByTitle)
                    .containsKey("Code Complete")
                    .containsValues(Collections.singletonList(codeComplete));

            assertThat(booksByTitle)
                    .containsKey("The Mythical Man-Month")
                    .containsValues(Collections.singletonList(mythicalManMonth));

            assertThat(booksByTitle)
                    .containsKey("Clean Code")
                    .containsValues(Collections.singletonList(cleanCode));
        }
    }
}

// TODO Continue in page 72
/*
TODO DOWNLOADING THE MOCKITO IMPLEMENTATION:
 Search in Maven repository 'org.mockito:mockito-core:2.+' find the latest version in Maven repository.
 */

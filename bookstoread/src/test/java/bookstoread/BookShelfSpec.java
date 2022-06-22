package bookstoread;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

/**
 * Is normaly written test classes tha end with Test or Tests, this book follow the behavior-driven-development
 * (BDD) naming convention. The *Test naming convention forces you to think that your unit tests are the only
 * quality assurance facility here we wanto to think in terms of behavior specification so Tests here will
 * end with Spec.
 *
 * With @DisplayName annotation you can name a class, method for more legible reading.
 */

@DisplayName("= BookShelf Specification =")
public class BookShelfSpec {

    @Test
    @DisplayName("is empty when no book is added to it")
    public void shelfEmptyWhenNoBookAdded() throws Exception {
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();
        Assertions.assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    @DisplayName("must contain two books in BookShelf when add 2 books")
    void bookshelfContainsTwoBooksWhenTwoBooksAreAdd() {
        BookShelf shelf = new BookShelf();
        shelf.add("Effective Java", "Code Complete");
        List<String> books = shelf.books();
        Assertions.assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }


}

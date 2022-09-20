package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

@DisplayName("A bookshelf progress")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfProgressSpec {
    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void init(Map<String, Book> books) throws Exception {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
        this.refactoring = books.get("Refactoring: Improving the Design of Existing Code");
    }

    @Test
    @DisplayName("is 0% completed and 100% to-read when no book is read yet")
    public void progress100PercentUnread() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode, refactoring);
        Progress progress = shelf.progress();
        assertThat(progress.completed()).isEqualTo(0);
        assertThat(progress.toRead()).isEqualTo(100);
    }

    @Disabled("until solve a issue with books in progress")
    @Test
    @DisplayName("is 40% completed, 20% in-progress, and 40% to-read when 2 books read, 1 book in progress and 2 books not read yet")
    public void progressWithCompletedInProgressAndToReadPercent() {
        effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
        effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));
        cleanCode.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
        cleanCode.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));
        codeComplete.startedReadingOn(LocalDate.of(2017, Month.JULY, 1));

        shelf.add(effectiveJava, cleanCode, codeComplete, mythicalManMonth, refactoring);
        Progress progress = shelf.progress();

        assertThat(progress.completed()).isEqualTo(40);
        assertThat(progress.inProgress()).isEqualTo(20);
        assertThat(progress.toRead()).isEqualTo(60);
    }

    @Test
    @DisplayName("is 40% completed and 60% to-read when 2 books are finished and 3 books not read yet")
    public void progressWithCompletedAndToReadPercent() {
        effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
        effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));
        cleanCode.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
        cleanCode.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));

        shelf.add(effectiveJava, cleanCode, codeComplete, mythicalManMonth, refactoring);
        Progress progress = shelf.progress();

        assertThat(progress.completed()).isEqualTo(40);
        assertThat(progress.toRead()).isEqualTo(60);
    }

}

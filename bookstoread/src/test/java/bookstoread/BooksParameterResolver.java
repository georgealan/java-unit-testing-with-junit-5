package bookstoread;

import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
JUnit 5 introduced the concept of ParameterResolver, which provides an API
to resolve parameters at runtime. You can either use a built-in parameter resolver
like TestInfoParameterResolver or provide your own resolver by implementing
ParameterResolver interface. ParameterResolver is part of the JUnit 5 extension
mechanism.
 */
public class BooksParameterResolver implements ParameterResolver {
    private final Map<String, Book> books;

    public BooksParameterResolver() {
        Map<String, Book> books = new HashMap<>();

        books.put("Effective Java", new Book("Effective Java", "Joshua Bloch",
                LocalDate.of(2008, Month.MAY, 8)));

        books.put("Code Complete", new Book("Code Complete", "Steve McConnel",
                LocalDate.of(2004, Month.JUNE, 9)));

        books.put("The Mythical Man-Month", new Book("The Mythical Man-Month", "Frederick Phillips Brooks",
                LocalDate.of(1975, Month.JANUARY, 1)));

        books.put("Clean Code", new Book("Clean Code", "Robert C. Martin",
                LocalDate.of(2008, Month.AUGUST, 1)));

        books.put("Refactoring: Improving the Design of Existing Code",
                new Book("Refactoring: Improving the Design of Existing Code", "Martin Fowler",
                        LocalDate.of(2002, Month.MARCH, 9)));

        this.books = books;
    }
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType()
                .getTypeName(), "java.util.Map<java.lang.String, bookstoread.Book>");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(Book.class));
        return store.getOrComputeIfAbsent("books", k -> getBooks());
    }

    private Object getBooks() {
        return books;
    }
}

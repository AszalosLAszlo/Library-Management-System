package com.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.library.service.BookService;
import com.library.service.AuthorService;
import com.library.repository.BookRepository;
import com.library.repository.AuthorRepository;

@Configuration
public class AppConfig {

    // Bean konfiguráció a BookService osztályhoz
    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }

    // Bean konfiguráció az AuthorService osztályhoz
    @Bean
    public AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorService(authorRepository);
    }
}

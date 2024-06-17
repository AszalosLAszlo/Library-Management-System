package com.library.service;

import com.library.entity.BookEntity;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private BookEntity book;

    @BeforeEach
    public void setUp() {
        book = new BookEntity();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor(null);
        book.setIsbn("123456789");
    }

    @Test
    public void testGetAllBooks() {
        List<BookEntity> bookList = new ArrayList<>();
        bookList.add(book);

        when(bookRepository.findAll()).thenReturn(bookList);

        List<BookEntity> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookEntity result = bookService.getBookById(1L);

        assertEquals("Test Book", result.getTitle());

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateOrUpdateBook() {
        when(bookRepository.save(book)).thenReturn(book);

        BookEntity result = bookService.createOrUpdateBook(book);

        assertEquals("Test Book", result.getTitle());

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testDeleteBook() {
        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}

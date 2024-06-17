package com.library.controller;

import com.library.entity.BookEntity;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Autowired
    private MockMvc mockMvc;

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
    public void testGetAllBooks() throws Exception {
        List<BookEntity> bookList = new ArrayList<>();
        bookList.add(book);

        when(bookService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Book"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookService.createOrUpdateBook(any(BookEntity.class))).thenReturn(book);

        String bookJson = "{ \"title\": \"Test Book\", \"author\": null, \"isbn\": \"123456789\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).createOrUpdateBook(any(BookEntity.class));
    }

    @Test
    public void testUpdateBook() throws Exception {
        when(bookService.createOrUpdateBook(any(BookEntity.class))).thenReturn(book);

        String bookJson = "{ \"title\": \"Updated Book\", \"author\": null, \"isbn\": \"123456789\" }";

        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Book"));

        verify(bookService, times(1)).createOrUpdateBook(any(BookEntity.class));
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(bookService, times(1)).deleteBook(1L);
    }
}

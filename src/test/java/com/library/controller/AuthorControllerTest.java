package com.library.controller;

import com.library.entity.AuthorEntity;
import com.library.service.AuthorService;
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
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @Autowired
    private MockMvc mockMvc;

    private AuthorEntity author;

    @BeforeEach
    public void setUp() {
        author = new AuthorEntity();
        author.setId(1L);
        author.setName("Test Author");
        author.setBiography("Test Biography");
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        List<AuthorEntity> authorList = new ArrayList<>();
        authorList.add(author);

        when(authorService.getAllAuthors()).thenReturn(authorList);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Author"));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void testGetAuthorById() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(author);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Author"));

        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    public void testCreateAuthor() throws Exception {
        when(authorService.createOrUpdateAuthor(any(AuthorEntity.class))).thenReturn(author);

        String authorJson = "{ \"name\": \"Test Author\", \"biography\": \"Test Biography\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Author"));

        verify(authorService, times(1)).createOrUpdateAuthor(any(AuthorEntity.class));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        when(authorService.createOrUpdateAuthor(any(AuthorEntity.class))).thenReturn(author);

        String authorJson = "{ \"name\": \"Updated Author\", \"biography\": \"Updated Biography\" }";

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Author"));

        verify(authorService, times(1)).createOrUpdateAuthor(any(AuthorEntity.class));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(authorService, times(1)).deleteAuthor(1L);
    }
}

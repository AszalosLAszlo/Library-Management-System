package com.library.service;

import com.library.entity.AuthorEntity;
import com.library.repository.AuthorRepository;
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
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorEntity author;

    @BeforeEach
    public void setUp() {
        author = new AuthorEntity();
        author.setId(1L);
        author.setName("Test Author");
        author.setBiography("Test Biography");
    }

    @Test
    public void testGetAllAuthors() {
        List<AuthorEntity> authorList = new ArrayList<>();
        authorList.add(author);

        when(authorRepository.findAll()).thenReturn(authorList);

        List<AuthorEntity> result = authorService.getAllAuthors();

        assertEquals(1, result.size());
        assertEquals("Test Author", result.get(0).getName());

        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorEntity result = authorService.getAuthorById(1L);

        assertEquals("Test Author", result.getName());

        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateOrUpdateAuthor() {
        when(authorRepository.save(author)).thenReturn(author);

        AuthorEntity result = authorService.createOrUpdateAuthor(author);

        assertEquals("Test Author", result.getName());

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    public void testDeleteAuthor() {
        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }
}

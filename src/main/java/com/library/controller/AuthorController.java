package com.library.controller;

import com.library.entity.AuthorEntity;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/")
    public List<AuthorEntity> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorEntity getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping("/")
    public AuthorEntity createAuthor(@RequestBody AuthorEntity author) {
        return authorService.createOrUpdateAuthor(author);
    }

    @PutMapping("/{id}")
    public AuthorEntity updateAuthor(@PathVariable Long id, @RequestBody AuthorEntity author) {
        author.setId(id);
        return authorService.createOrUpdateAuthor(author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}

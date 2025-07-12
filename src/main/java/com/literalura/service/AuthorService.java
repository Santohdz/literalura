package com.literalura.service;

import com.literalura.model.Author;
import com.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Author> listarAutores() {
        return authorRepository.findAllWithBooks();
    }

    @Transactional(readOnly = true)
    public List<Author> listarAutoresVivosEnAnio(Integer anio) {
        return authorRepository.findAliveInYearWithBooks(anio);
    }
}

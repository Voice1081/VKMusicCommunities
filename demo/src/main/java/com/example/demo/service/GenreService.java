package com.example.demo.service;

import com.example.demo.domain.Genre;
import com.example.demo.domain.Record;
import com.example.demo.domain.repository.GenreRepository;
import com.example.demo.domain.repository.RecordRepository;
import com.example.demo.service.util.PreworkChecker;
import java.util.List;
import java.util.UUID;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreService.class);

    private final GenreRepository genreRepository;
    private final PreworkChecker preworkChecker;

    public GenreService(GenreRepository genreRepository, PreworkChecker preworkChecker) {
        this.genreRepository = genreRepository;
        this.preworkChecker = preworkChecker;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Genre saveNewGenre(final Genre genre) {
        preworkChecker.throwIfGenreAlreadyExists(genre.getId());
        Genre newGenre = genreRepository.save(genre);
        LOGGER.info("Created new genre {}", newGenre);
        return newGenre;
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Genre updateGenre(final Genre genre) throws NotFoundException {
        preworkChecker.throwIfGenreDoesNotExists(genre.getId());
        Genre savedGenre = genreRepository.save(genre);
        LOGGER.info("Updated genre object {}", genre);
        return savedGenre;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteGenre(String id) throws NotFoundException {
        preworkChecker.throwIfGenreDoesNotExists(id);
        genreRepository.deleteById(id);
        LOGGER.info("Deleted record object with id {}", id);
    }

    public Genre getById(String id) throws NotFoundException {
        preworkChecker.throwIfGenreDoesNotExists(id);
        return genreRepository.findById(id).get();
    }
}

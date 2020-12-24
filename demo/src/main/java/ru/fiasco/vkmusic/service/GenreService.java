package ru.fiasco.vkmusic.service;

import ru.fiasco.vkmusic.domain.Genre;
import ru.fiasco.vkmusic.domain.repository.GenreRepository;
import ru.fiasco.vkmusic.service.util.PreworkChecker;
import java.util.List;
import java.util.stream.Collectors;
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
        return genreRepository.findAll().stream().sorted().collect(Collectors.toList());
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

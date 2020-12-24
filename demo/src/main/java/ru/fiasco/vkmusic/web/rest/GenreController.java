package ru.fiasco.vkmusic.web.rest;

import ru.fiasco.vkmusic.domain.Genre;
import ru.fiasco.vkmusic.service.GenreService;
import java.util.List;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class GenreController {
    private GenreService genreService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping(path = "/genres")
    public ResponseEntity<Genre> saveNewRecord(final @RequestBody Genre genre) {
        LOGGER.info("Received REST request to save new genre {}", genre);
        Genre savedRecord = genreService.saveNewGenre(genre);
        return ResponseEntity.ok(savedRecord);
    }



    @DeleteMapping(path = "/genres")
    public ResponseEntity<Void> deleteRecord(final @RequestParam(value = "id") String id) throws NotFoundException {
        LOGGER.info("Received REST request to delete genre with id {}", id);
        genreService.deleteGenre(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/genres/all")
    public ResponseEntity<List<Genre>> getAll(){
        LOGGER.info("Received REST request to get all genres");
        List<Genre> all = genreService.getAll();
        return ResponseEntity.ok(all);
    }

    @PutMapping(path = "/genres")
    public ResponseEntity<Genre> updateRecord(final @RequestBody Genre genre) throws NotFoundException {
        LOGGER.info("Received REST request to update genre {}", genre);
        Genre updatedRecord = genreService.updateGenre(genre);
        return ResponseEntity.ok(updatedRecord);
    }
}

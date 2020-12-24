package ru.fiasco.vkmusic.domain.repository;

import ru.fiasco.vkmusic.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
}

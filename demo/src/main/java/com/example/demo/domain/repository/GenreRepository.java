package com.example.demo.domain.repository;

import com.example.demo.domain.Genre;
import com.example.demo.domain.Record;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
}

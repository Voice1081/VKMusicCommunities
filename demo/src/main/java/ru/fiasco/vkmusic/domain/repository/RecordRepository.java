package ru.fiasco.vkmusic.domain.repository;

import ru.fiasco.vkmusic.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {
    @Query("select tt from Record tt where tt.link = :link")
    List<Record> getAllByLink(final @Param("link") String link);
}
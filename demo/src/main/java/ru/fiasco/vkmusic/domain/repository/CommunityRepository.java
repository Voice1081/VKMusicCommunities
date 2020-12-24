package ru.fiasco.vkmusic.domain.repository;

import ru.fiasco.vkmusic.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("select tt from Community tt where tt.link = :link")
    List<Community> getAllByLink(final @Param("link") String link);
}

package com.example.demo.domain.repository;

import com.example.demo.domain.Community;
import com.example.demo.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommunityRepository extends JpaRepository<Community, UUID> {
    @Query("select tt from Community tt where tt.link = :link")
    List<Community> getAllByLink(final @Param("link") String link);
}

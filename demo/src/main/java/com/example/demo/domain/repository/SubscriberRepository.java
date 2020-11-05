package com.example.demo.domain.repository;

import com.example.demo.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {
    @Query("select tt from Subscriber tt where tt.nickname = :nickname")
    List<Subscriber> getAllByNickname(final String nickname);
}

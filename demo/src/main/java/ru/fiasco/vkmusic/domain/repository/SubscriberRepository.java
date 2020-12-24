package ru.fiasco.vkmusic.domain.repository;

import ru.fiasco.vkmusic.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {
    @Query("select tt from Subscriber tt where tt.nickname = :nickname")
    List<Subscriber> getAllByNickname(final String nickname);
    @Query("select tt from Subscriber tt where tt.chatId = :chatId")
    List<Subscriber> getAllByChatId(@Param("chatId") final long chatId);
}
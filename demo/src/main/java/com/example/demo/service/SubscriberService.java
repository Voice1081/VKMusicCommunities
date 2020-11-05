package com.example.demo.service;

import com.example.demo.domain.Community;
import com.example.demo.domain.Subscriber;
import com.example.demo.domain.repository.CommunityRepository;
import com.example.demo.domain.repository.SubscriberRepository;
import com.example.demo.service.util.PreworkChecker;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberService.class);

    private SubscriberRepository subscriberRepository;
    private PreworkChecker preworkChecker;

    public SubscriberService(SubscriberRepository subscriberRepository, PreworkChecker preworkChecker) {
        this.subscriberRepository = subscriberRepository;
        this.preworkChecker = preworkChecker;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Subscriber saveNewSubscriber(final Subscriber subscriber) {
        Subscriber newSubscriber = subscriberRepository.save(subscriber);
        preworkChecker.throwIfSubscriberAlreadyExists(newSubscriber.getId());
        LOGGER.info("Created new Subscriber object {}", subscriber);
        return newSubscriber;
    }

    public List<Subscriber> getAll() {
        return subscriberRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Subscriber updateSubscriber(final Subscriber subscriber) throws NotFoundException {
        preworkChecker.throwIfSubscriberDoesNotExists(subscriber.getId());
        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        LOGGER.info("Updated subscriber object {}", subscriber);
        return savedSubscriber;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteSubscriber(UUID id) throws NotFoundException {
        preworkChecker.throwIfSubscriberDoesNotExists(id);
        subscriberRepository.deleteById(id);
    }

    public List<Subscriber> getAllByNickname(String nickname) {
        return subscriberRepository.getAllByNickname(nickname);
    }
}

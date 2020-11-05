package com.example.demo.service;

import com.example.demo.domain.Community;
import com.example.demo.domain.Subscriber;
import com.example.demo.domain.repository.CommunityRepository;
import com.example.demo.domain.repository.SubscriberRepository;
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

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Subscriber saveNewSubscriber(final Subscriber subscriber) {
        Subscriber newSubscriber = subscriberRepository.save(subscriber);
        LOGGER.info("Created new Subscriber object {}", subscriber);
        return newSubscriber;
    }

    public List<Subscriber> getAll() {
        return subscriberRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Subscriber updateSubscriber(final Subscriber subscriber) throws NotFoundException {
        if(subscriberRepository.existsById(subscriber.getId())) {
            Subscriber savedSubscriber = subscriberRepository.save(subscriber);
            LOGGER.info("Updated subscriber object {}", subscriber);
            return savedSubscriber;
        }
        else{
            throw new NotFoundException(String.format("Not found subscriber with id %s", subscriber.getId()));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteSubscriber(UUID id) throws NotFoundException {
        if(subscriberRepository.existsById(id)) {
            subscriberRepository.deleteById(id);
        }
        else{
            throw new NotFoundException(String.format("Not found subscriber with id %s", id));
        }
    }

    public List<Subscriber> getAllByNickname(String nickname){
        return subscriberRepository.getAllByNickname(nickname);
    }
}

package com.example.demo.service.util;

import com.example.demo.domain.repository.CommunityRepository;
import com.example.demo.domain.repository.RecordRepository;
import com.example.demo.domain.repository.SubscriberRepository;
import com.example.demo.web.rest.errors.exceptions.ApiException;
import com.example.demo.web.rest.errors.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PreworkChecker {
    private final RecordRepository recordRepository;
    private final CommunityRepository communityRepository;
    private final SubscriberRepository subscriberRepository;

    public PreworkChecker(RecordRepository recordRepository, CommunityRepository communityRepository, SubscriberRepository subscriberRepository) {
        this.recordRepository = recordRepository;
        this.communityRepository = communityRepository;
        this.subscriberRepository = subscriberRepository;
    }

    public void throwIfRecordAlreadyExists(final UUID recordId) {
        if (recordId != null && recordRepository.existsById(recordId)) {
            throw new ApiException(String.format("Record with id %s already exists", recordId), HttpStatus.BAD_REQUEST);
        }
    }

    public void throwIfRecordDoesNotExists(final UUID recordId) {
        if (recordId == null) {
            throw new ApiException("Record id is empty", HttpStatus.BAD_REQUEST);
        }
        if (!recordRepository.existsById(recordId)) {
            throw new ApiException(String.format("Record with id %s does not exists", recordId), HttpStatus.NOT_FOUND);
        }
    }

    public void throwIfCommunityAlreadyExists(final Long communityId) {
        if (communityId != null && communityRepository.existsById(communityId)) {
            throw new ApiException(String.format("Community with id %s already exists", communityId), HttpStatus.BAD_REQUEST);
        }
    }

    public void throwIfCommunityDoesNotExists(final Long communityId) {
        if (communityId == null) {
            throw new BadRequestException("Community id is empty");
        }
        if (!communityRepository.existsById(communityId)) {
            throw new ApiException(String.format("Community with id %s does not exists", communityId), HttpStatus.NOT_FOUND);
        }
    }

    public void throwIfSubscriberAlreadyExists(final UUID subscriberId) {
        if (subscriberId != null && subscriberRepository.existsById(subscriberId)) {
            throw new ApiException(String.format("Subscriber with id %s already exists", subscriberId), HttpStatus.BAD_REQUEST);
        }
    }

    public void throwIfSubscriberDoesNotExists(final UUID subscriberId) {
        if (subscriberId == null) {
            throw new ApiException("Subscriber id is empty", HttpStatus.BAD_REQUEST);
        }
        if (!subscriberRepository.existsById(subscriberId)) {
            throw new ApiException(String.format("Subscriber with id %s does not exists", subscriberId), HttpStatus.NOT_FOUND);
        }
    }
}

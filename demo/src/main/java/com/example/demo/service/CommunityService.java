package com.example.demo.service;

import com.example.demo.domain.Community;
import com.example.demo.domain.repository.CommunityRepository;
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
public class CommunityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityService.class);

    private CommunityRepository communityRepository;

    private PreworkChecker preworkChecker;

    public CommunityService(CommunityRepository communityRepository, PreworkChecker preworkChecker) {
        this.communityRepository = communityRepository;
        this.preworkChecker = preworkChecker;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Community saveNewCommunity(final Community community) {
        preworkChecker.throwIfCommunityAlreadyExists(community.getId());
        Community newCommunity = communityRepository.save(community);
        LOGGER.info("Created new Community object {}", community);
        return newCommunity;
    }

    public List<Community> getAll() {
        return communityRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Community updateCommunity(final Community community) throws NotFoundException {
        preworkChecker.throwIfCommunityDoesNotExists(community.getId());
        Community savedCommunity = communityRepository.save(community);
        LOGGER.info("Updated community object {}", community);
        return savedCommunity;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCommunity(UUID id) throws NotFoundException {
        preworkChecker.throwIfCommunityDoesNotExists(id);
        communityRepository.deleteById(id);
    }

    public List<Community> getAllByLink(String link) {
        return communityRepository.getAllByLink(link);
    }
}

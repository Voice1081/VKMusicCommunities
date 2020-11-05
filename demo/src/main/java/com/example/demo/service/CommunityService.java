package com.example.demo.service;

import com.example.demo.domain.Community;
import com.example.demo.domain.repository.CommunityRepository;
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

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Community saveNewCommunity(final Community community) {
        Community newCommunity = communityRepository.save(community);
        LOGGER.info("Created new Community object {}", community);
        return newCommunity;
    }

    public List<Community> getAll() {
        return communityRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Community updateCommunity(final Community community) throws NotFoundException {
        if(communityRepository.existsById(community.getId())) {
            Community savedCommunity = communityRepository.save(community);
            LOGGER.info("Updated community object {}", community);
            return savedCommunity;
        }
        else{
            throw new NotFoundException(String.format("Not found community with id %s", community.getId()));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCommunity(UUID id) throws NotFoundException {
        if(communityRepository.existsById(id)) {
            communityRepository.deleteById(id);
        }
        else{
            throw new NotFoundException(String.format("Not found community with id %s", id));
        }
    }

    public List<Community> getAllByLink(String link){
        return communityRepository.getAllByLink(link);
    }
}

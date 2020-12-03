package com.example.demo.service;

import com.example.demo.domain.Community;
import com.example.demo.domain.repository.CommunityRepository;
import com.example.demo.dto.CommunityDTO;
import com.example.demo.service.util.CommunityUtil;
import com.example.demo.service.util.PreworkChecker;
import com.vk.api.sdk.objects.groups.GroupFull;
import java.time.Instant;
import java.util.HashSet;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityService.class);

    private final CommunityRepository communityRepository;
    private final PreworkChecker preworkChecker;
    private final VkApiRequestExecutor vkApiRequestExecutor;

    public CommunityService(CommunityRepository communityRepository,
                            PreworkChecker preworkChecker,
                            VkApiRequestExecutor vkApiRequestExecutor) {
        this.communityRepository = communityRepository;
        this.preworkChecker = preworkChecker;
        this.vkApiRequestExecutor = vkApiRequestExecutor;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Community saveNewCommunity(final CommunityDTO communityDTO) {
        Community newCommunity = communityRepository.save(getAllCommunityInfo(communityDTO.getLink(), communityDTO.getGenre(), true));
        LOGGER.info("Created new community object {}", newCommunity);
        return newCommunity;
    }

    public List<Community> getAll() {
        return communityRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Community updateCommunity(final CommunityDTO communityDTO) throws NotFoundException {
        Community updatedCommunity = communityRepository.save(getAllCommunityInfo(communityDTO.getLink(), communityDTO.getGenre(), false));
        LOGGER.info("Updated community object {}", updatedCommunity);
        return updatedCommunity;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCommunity(Long id) throws NotFoundException {
        preworkChecker.throwIfCommunityDoesNotExists(id);
        communityRepository.deleteById(id);
    }

    public List<Community> getAllByLink(String link) {
        return communityRepository.getAllByLink(link);
    }

    public List<Community> getAllMatchingByGenresCommunity(List<String> genres) {
        List<Community> communityList = communityRepository.findAll();
        HashSet<String> distinctGenres = new HashSet<>(genres);

        return communityList.stream().filter(community -> {
            HashSet<String> distinctCommunityGenres = new HashSet<>(community.getGenres());
            distinctCommunityGenres.retainAll(distinctGenres);
            return distinctCommunityGenres.size() > 0;
        }).collect(Collectors.toList());
    }

    public Community getAllCommunityInfo(final String link, final List<String> genres, final boolean isNew) {
        String shortname = CommunityUtil.getShortCommunityNameFromLink(link);
        GroupFull groupFull = vkApiRequestExecutor.getCommunityDescription(shortname);
        if (isNew) {
            preworkChecker.throwIfCommunityAlreadyExists(groupFull.getId().longValue());
        } else {
            preworkChecker.throwIfCommunityDoesNotExists(groupFull.getId().longValue());
        }

        Community community = new Community();
        community.setCommunityId(groupFull.getId().longValue());
        community.setDomain(shortname);
        community.setGenres(genres);
        community.setLink(link);
        community.setName(groupFull.getName());
        community.setLastPostTime(Instant.now().getEpochSecond());

        return community;
    }
}

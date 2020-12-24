package ru.fiasco.vkmusic.web.rest;

import ru.fiasco.vkmusic.domain.Community;
import ru.fiasco.vkmusic.dto.CommunityDTO;
import ru.fiasco.vkmusic.service.CommunityService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CommunityController {
    private final CommunityService communityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityController.class);

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping(path = "/communities")
    public ResponseEntity<Community> saveNewCommunity(final @RequestBody CommunityDTO communityDTO) {
        LOGGER.info("Received REST request to save new community {}", communityDTO);
        Community savedCommunity = communityService.saveNewCommunity(communityDTO);
        return ResponseEntity.ok(savedCommunity);
    }


    @DeleteMapping(path = "/communities")
    public ResponseEntity<Void> deleteCommunity(final @RequestParam(value = "id") long id) throws NotFoundException {
        LOGGER.info("Received REST request to delete community with id {}", id);
        communityService.deleteCommunity(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/communities/link")
    public ResponseEntity<List<Community>> getAllByLink(final @RequestParam(value = "link") String link) {
        LOGGER.info("Received REST request to get all communities by link {}", link);
        List<Community> allByLink = communityService.getAllByLink(link);
        return ResponseEntity.ok(allByLink);
    }

    @GetMapping(path = "/communities/all")
    public ResponseEntity<List<Community>> getAll() {
        LOGGER.info("Received REST request to get all communities");
        List<Community> all = communityService.getAll();
        return ResponseEntity.ok(all);
    }

    @PutMapping(path = "/communities")
    public ResponseEntity<Community> updateCommunity(final @RequestBody CommunityDTO community) throws NotFoundException {
        LOGGER.info("Received REST request to update community {}", community);
        Community updatedCommunity = communityService.updateCommunity(community);
        return ResponseEntity.ok(updatedCommunity);
    }
}

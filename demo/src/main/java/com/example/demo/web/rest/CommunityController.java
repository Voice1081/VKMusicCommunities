package com.example.demo.web.rest;

import com.example.demo.domain.Community;
import com.example.demo.domain.Record;
import com.example.demo.dto.CommunityDTO;
import com.example.demo.dto.RecordDTO;
import com.example.demo.service.CommunityService;
import com.example.demo.service.RecordService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class CommunityController {
    private CommunityService communityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityController.class);

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping(path = "/communities")
    public ResponseEntity<Community> saveNewCommunity(final @RequestBody CommunityDTO communityDTO) {
        Community community = new Community(communityDTO);
        LOGGER.info("Received REST request to save new community {}", community);
        Community savedCommunity = communityService.saveNewCommunity(community);
        return ResponseEntity.ok(savedCommunity);
    }



    @DeleteMapping(path = "/communities")
    public ResponseEntity<Void> deleteCommunity(final @RequestParam(value = "id") UUID id) throws NotFoundException {
        LOGGER.info("Received REST request to delete community with id {}", id);
        communityService.deleteCommunity(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/communities/{link}")
    public ResponseEntity<List<Community>> getAllByLink(final @PathVariable(value = "link") String link) {
        LOGGER.info("Received REST request to get all communities by link {}", link);
        List<Community> allByLink = communityService.getAllByLink(link);
        return ResponseEntity.ok(allByLink);
    }

    @GetMapping(path = "/communities/all")
    public ResponseEntity<List<Community>> getAll(){
        LOGGER.info("Received REST request to get all communities");
        List<Community> all = communityService.getAll();
        return ResponseEntity.ok(all);
    }

    @PutMapping(path = "/communities")
    public ResponseEntity<Community> updateCommunity(final @RequestBody Community community) throws NotFoundException {
        LOGGER.info("Received REST request to update community {}", community);
        Community updatedCommunity = communityService.updateCommunity(community);
        return ResponseEntity.ok(updatedCommunity);
    }
}

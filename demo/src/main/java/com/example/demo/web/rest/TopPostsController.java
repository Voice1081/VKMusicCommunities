package com.example.demo.web.rest;

import com.example.demo.domain.Record;
import com.example.demo.dto.TopPostRequest;
import com.example.demo.service.PostsService;
import io.swagger.annotations.Api;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "top-posts")
@RequestMapping(path = "/api")
public class TopPostsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopPostsController.class);

    private final PostsService postsService;

    public TopPostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    public ResponseEntity<List<Record>> getTopPosts(
        final @RequestBody TopPostRequest topPostRequest) {
        LOGGER
            .info("REST request to get top posts for {} with genres {}", topPostRequest.getRange(),
                String.join(", ", topPostRequest.getGenres()));
        return ResponseEntity.ok(postsService
            .getRecordsForGenreList(topPostRequest.getGenres(), topPostRequest.getRange()));
    }
}

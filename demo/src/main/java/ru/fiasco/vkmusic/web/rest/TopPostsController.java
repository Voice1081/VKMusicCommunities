package ru.fiasco.vkmusic.web.rest;

import ru.fiasco.vkmusic.dto.PostDto;
import ru.fiasco.vkmusic.dto.enumeration.PostRange;
import ru.fiasco.vkmusic.service.PostsService;
import io.swagger.annotations.Api;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(path = "/topPosts")
    public ResponseEntity<List<PostDto>> getTopPosts(
        final @RequestParam("range") PostRange postRange,
        final @RequestParam("genres") String[] genres) {
        LOGGER
            .info("REST request to get top posts for {} with genres {}", postRange,
                String.join(", ", genres));
        return ResponseEntity.ok(postsService
            .getRecordsForGenreList(Arrays.asList(genres), postRange));
    }
}

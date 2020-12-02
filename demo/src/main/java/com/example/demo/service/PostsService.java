package com.example.demo.service;

import com.example.demo.domain.Community;
import com.example.demo.domain.Record;
import com.example.demo.dto.PostMeta;
import com.example.demo.dto.enumeration.PostRange;
import com.example.demo.service.util.PostUtil;
import com.google.gson.JsonElement;
import com.vk.api.sdk.objects.wall.WallPostFull;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PostsService {
    private final VkApiRequestExecutor vkApiRequestExecutor;
    private final CommunityService communityService;

    public PostsService(VkApiRequestExecutor vkApiRequestExecutor,
                        CommunityService communityService) {
        this.vkApiRequestExecutor = vkApiRequestExecutor;
        this.communityService = communityService;
    }

    public List<Record> getRecordsForGenreList(final List<String> genres, final PostRange range) {
        List<Community> communities = communityService.getAllMatchingByGenresCommunity(genres);
        Map<Long, JsonElement> rangedPostsMeta =
            vkApiRequestExecutor.getRangedPostsMeta(range, communities);

        List<PostMeta> receivedMeta = PostUtil.parseReceivedMeta(rangedPostsMeta);
        receivedMeta.sort(Comparator.comparing(PostMeta::getLikes).reversed());

        int offset = 0;
        List<Record> records = new ArrayList<>();
        while (records.size() < 10) {
            int nextOffset = getNextOffset(receivedMeta.size(), offset);
            List<WallPostFull> posts =
                vkApiRequestExecutor.getFullPosts(receivedMeta.subList(offset, nextOffset));
            List<Record> desiredRecords =
                posts.stream().filter(post -> PostUtil.checkIsPostDesired(post, genres))
                    .map(this::mapPostToRecord).collect(Collectors.toList());

            records.addAll(desiredRecords);
            if (nextOffset - offset < 10) {
                break;
            }
            offset = nextOffset;
        }

        return records;
    }

    private Record mapPostToRecord(final WallPostFull post) {
        Record record = new Record();
        record.setId(UUID.randomUUID());
        record.setLink(String.format("https://vk.com/wall%s_%s", post.getOwnerId(), post.getId()));
        record.setLikes(post.getLikes().getCount());
        record.setDate(ZonedDateTime.from(Instant.ofEpochSecond(post.getDate())));

        return record;
    }

    private int getNextOffset(int postsSize, int offset) {
        int increasedOffset = offset + 10;
        return Math.min(postsSize, increasedOffset);
    }

}

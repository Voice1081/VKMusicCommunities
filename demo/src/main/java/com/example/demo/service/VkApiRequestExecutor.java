package com.example.demo.service;

import com.example.demo.domain.Community;
import com.example.demo.dto.PostMeta;
import com.example.demo.dto.enumeration.PostRange;
import com.example.demo.web.rest.errors.exceptions.BadGatewayException;
import com.example.demo.web.rest.errors.exceptions.BadRequestException;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VkApiRequestExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(VkApiRequestExecutor.class);

    private final VkApiClient vkApiClient;
    private final UserActor userActor;

    public VkApiRequestExecutor(VkApiClient vkApiClient,
                                UserActor userActor) {
        this.vkApiClient = vkApiClient;
        this.userActor = userActor;
    }

    public GroupFull getCommunityDescription(final String shortname) {
        try {
            List<GroupFull> groupFullList =
                vkApiClient.groups().getById(userActor).groupId(shortname).execute();
            if (groupFullList.size() == 0) {
                throw new BadRequestException("Сообщество с такой ссылкой не найдено");
            } else {
                return groupFullList.get(0);
            }
        } catch (ClientException e) {
            LOGGER.error("Client error while receiving group info with shortname {}", shortname, e);
            throw new BadGatewayException(
                "Some client exception while accessing VK API, more info in logs");
        } catch (ApiException e) {
            LOGGER.error("Api error while receiving group info with shortname {}", shortname, e);
            throw new BadGatewayException(
                "Some api exception while accessing VK API, more info in logs");
        }
    }

    public WallPostFull getLastPost(final String domain) {
        try {
            GetResponse response =
                vkApiClient.wall().get(userActor).domain(domain).offset(0).count(2)
                    .execute();
            if (response.getItems().size() > 0) {
                if (response.getItems().get(0).getIsPinned() != null && response.getItems().get(0).getIsPinned() == 1) {
                    return response.getItems().get(1);
                } else {
                    return response.getItems().get(0);
                }
            } else {
                return null;
            }
        } catch (ClientException e) {
            LOGGER.error("Client error while receiving last post for domain {}", domain, e);
            throw new BadGatewayException(
                "Some client exception while accessing VK API, more info in logs");
        } catch (ApiException e) {
            LOGGER.error("Api error while receiving last post for domain {}", domain, e);
            throw new BadGatewayException(
                "Some api exception while accessing VK API, more info in logs");
        }
    }

    public List<WallPostFull> getFullPosts(final List<PostMeta> postMetaList) {
        List<String> posts = postMetaList.stream()
            .map(postMeta -> String.format("-%s_%s", postMeta.getCommunityId(), postMeta.getId()))
            .collect(Collectors.toList());

        if (posts.size() == 0) {
            return Collections.emptyList();
        }

        try {
            return vkApiClient.wall().getById(userActor, posts).execute();
        } catch (ClientException e) {
            LOGGER.error("Client error while receiving full posts with ids {}", posts, e);
            throw new BadGatewayException(
                "Some client exception while accessing VK API, more info in logs");
        } catch (ApiException e) {
            LOGGER.error("Api error while receiving full posts with ids {}", posts, e);
            throw new BadGatewayException(
                "Some api exception while accessing VK API, more info in logs");
        }

    }

    public Map<Long, JsonElement> getRangedPostsMeta(PostRange postRange,
                                                     List<Community> communities) {
        long dateToStartSearch =
            Instant.now().getEpochSecond() - TimeUnit.DAYS.toSeconds(postRange.getDayCount());
        Map<Long, JsonElement> responseMap = new HashMap<>();

        for (Community community : communities) {
            try {
                JsonElement response =
                    vkApiClient.execute().storageFunction(userActor, "getLatestPosts")
                        .unsafeParam("domain", community.getDomain())
                        .unsafeParam("deadline", dateToStartSearch).execute();
                LOGGER.debug("For community {} received such JSON with meta info: {}",
                    community.getLink(), response);
                responseMap.put(community.getCommunityId(), response);
            } catch (ClientException e) {
                LOGGER.error("Client error while receiving posts info for community {}",
                    community.getLink(), e);
                throw new BadGatewayException(
                    "Some client exception while accessing VK API, more info in logs");
            } catch (ApiException e) {
                LOGGER.error("API error while receiving posts info for community {}",
                    community.getLink(), e);
                throw new BadGatewayException(
                    "Some api exception while accessing VK API, more info in logs");
            }

        }
        return responseMap;
    }
}

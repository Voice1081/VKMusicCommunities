package com.example.demo.service.util;

import com.example.demo.dto.PostMeta;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.wall.WallPostFull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostUtil {
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#([\\w_-]+)");
    private static final Pattern VK_LINK_PATTERN = Pattern.compile("(\\[([\\w\\?\\:\\-\\=\\/\\.]+)\\|([\\w\\s\\-а-яА-Я]+)\\])");


    public static List<PostMeta> parseReceivedMeta(final Map<Long, JsonElement> responseMap) {
        List<PostMeta> allMeta = new LinkedList<>();
        responseMap.keySet().forEach(domain -> {
            JsonElement domainResponse = responseMap.get(domain);
            JsonArray responseArray = domainResponse.getAsJsonArray();
            responseArray.forEach(responsePart -> {
                JsonObject responseChunk = responsePart.getAsJsonObject();
                JsonArray ids = responseChunk.get("ids").getAsJsonArray();
                JsonArray dates = responseChunk.get("dates").getAsJsonArray();
                JsonArray likes = responseChunk.get("likes").getAsJsonArray();

                for (int i = 0; i < ids.size(); i++) {
                    PostMeta postMeta = new PostMeta();
                    postMeta.setCommunityId(domain);
                    postMeta.setId(ids.get(i).getAsString());
                    postMeta.setDate(dates.get(i).getAsInt());
                    postMeta.setLikes(likes.get(i).getAsInt());

                    allMeta.add(postMeta);
                }
            });
        });

        return allMeta;
    }

    public static String getLabelFromText(final String message) {
        String rawLabel = message.split("\n")[0];
        return unlinkLabel(rawLabel);
    }

    private static String unlinkLabel(final String label) {
        String newLink = label;

        Matcher matcher = VK_LINK_PATTERN.matcher(label);
        while(matcher.find()) {
            newLink = newLink.replace(matcher.group(0), matcher.group(3));
        }

        return newLink;
    }

    public static boolean checkIsPostDesired(final WallPostFull post, final List<String> genres) {
        List<String> foundGenres = getAllHashtagsFromString(post.getText());
        HashSet<String> stringHashSet = new HashSet<>(foundGenres);
        stringHashSet.retainAll(new HashSet<>(genres));
        return stringHashSet.size() > 0;
    }

    private static List<String> getAllHashtagsFromString(final String message) {
        Matcher matcher = HASHTAG_PATTERN.matcher(message);
        List<String> hashtags = new ArrayList<>();

        while(matcher.find()) {
            hashtags.add(matcher.group(1));
        }

        return hashtags;
    }
}

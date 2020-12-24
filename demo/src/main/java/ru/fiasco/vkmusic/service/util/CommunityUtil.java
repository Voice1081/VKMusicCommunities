package ru.fiasco.vkmusic.service.util;

import ru.fiasco.vkmusic.web.rest.errors.exceptions.BadRequestException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommunityUtil {
    private static final Pattern SHORTNAME_PATTERN = Pattern.compile("vk\\.com/([\\w-_]+)");

    public static String getShortCommunityNameFromLink(final String link) {
        Matcher matcher = SHORTNAME_PATTERN.matcher(link);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new BadRequestException("Некорректная ссылка на сообщество");
        }
    }

}

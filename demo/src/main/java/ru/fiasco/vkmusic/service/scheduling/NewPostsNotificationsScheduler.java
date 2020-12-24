package ru.fiasco.vkmusic.service.scheduling;

import ru.fiasco.vkmusic.bot.Bot;
import ru.fiasco.vkmusic.domain.Community;
import ru.fiasco.vkmusic.domain.Subscriber;
import ru.fiasco.vkmusic.service.CommunityService;
import ru.fiasco.vkmusic.service.SubscriberService;
import ru.fiasco.vkmusic.service.VkApiRequestExecutor;
import ru.fiasco.vkmusic.service.util.PostUtil;
import com.vk.api.sdk.objects.wall.WallPostFull;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewPostsNotificationsScheduler {
    private static final Logger LOGGER =
        LoggerFactory.getLogger(NewPostsNotificationsScheduler.class);

    private final Bot bot;
    private final CommunityService communityService;
    private final SubscriberService subscriberService;
    private final VkApiRequestExecutor vkApiRequestExecutor;

    public NewPostsNotificationsScheduler(Bot bot,
                                          CommunityService communityService,
                                          SubscriberService subscriberService,
                                          VkApiRequestExecutor vkApiRequestExecutor) {
        this.bot = bot;
        this.communityService = communityService;
        this.subscriberService = subscriberService;
        this.vkApiRequestExecutor = vkApiRequestExecutor;
    }

    @Scheduled(fixedDelay = 15000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifySubscribers() {
        List<Subscriber> subscribers = subscriberService.getAll();

        subscribers.forEach(subscriber -> {
            List<Community> communitiesToCheck =
                communityService.getAllMatchingByGenresCommunity(subscriber.getSubscribersGenres());

            communitiesToCheck.forEach(community -> {
                WallPostFull lastPost = vkApiRequestExecutor.getLastPost(community.getDomain());
                if (lastPost == null) {
                    LOGGER.info("No posts in community {}", community.getName());
                } else if (lastPost.getDate() > community.getLastPostTime() &&
                    PostUtil.checkIsPostDesired(lastPost, subscriber.getSubscribersGenres())) {
                    LOGGER.info("New post in community {}", community.getName());
                    bot.sendMessageToSubscriber(subscriber,
                        buildNewPostNotificationMessage(community, lastPost));
                    community.setLastPostTime(lastPost.getDate().longValue());
                    communityService.updateCommunity(community);
                }
            });
        });
    }

    private String buildNewPostNotificationMessage(final Community community,
                                                   final WallPostFull newPost) {
        StringBuilder sb =
            new StringBuilder(String.format("В сообществе %s новая запись!", community.getName()));
        sb.append("\n");
        sb.append(String.format("https://vk.com/wall%s_%s", newPost.getOwnerId(), newPost.getId()));
        return sb.toString();
    }

}

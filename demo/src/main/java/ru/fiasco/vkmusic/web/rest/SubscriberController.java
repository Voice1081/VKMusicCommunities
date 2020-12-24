package ru.fiasco.vkmusic.web.rest;

import ru.fiasco.vkmusic.domain.Subscriber;
import ru.fiasco.vkmusic.dto.SubscriberDTO;
import ru.fiasco.vkmusic.service.SubscriberService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class SubscriberController {
    private SubscriberService subscriberService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberController.class);

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping(path = "/subscribers")
    public ResponseEntity<Subscriber> saveNewSubscriber(final @RequestBody SubscriberDTO subscriberDTO) {
        Subscriber subscriber = new Subscriber(subscriberDTO);
        LOGGER.info("Received REST request to save new subscriber {}", subscriber);
        Subscriber savedSubscriber = subscriberService.saveNewSubscriber(subscriber);
        return ResponseEntity.ok(savedSubscriber);
    }



    @DeleteMapping(path = "/subscribers")
    public ResponseEntity<Void> deleteSubscriber(final @RequestParam(value = "id") UUID id) throws NotFoundException {
        LOGGER.info("Received REST request to delete subscriber with id {}", id);
        subscriberService.deleteSubscriber(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/subscribers/{nickname}")
    public ResponseEntity<List<Subscriber>> getAllByNickname(final @PathVariable(value = "nickname") String nickname) {
        LOGGER.info("Received REST request to get all subscribers by nickname {}", nickname);
        List<Subscriber> allByNickname = subscriberService.getAllByNickname(nickname);
        return ResponseEntity.ok(allByNickname);
    }

    @GetMapping(path = "/subscribers/all")
    public ResponseEntity<List<Subscriber>> getAll(){
        LOGGER.info("Received REST request to get all subscribers");
        List<Subscriber> all = subscriberService.getAll();
        return ResponseEntity.ok(all);
    }

    @PutMapping(path = "/subscribers")
    public ResponseEntity<Subscriber> updateSubscriber(final @RequestBody Subscriber subscriber) throws NotFoundException {
        LOGGER.info("Received REST request to update subscriber {}", subscriber);
        Subscriber updatedSubscriber = subscriberService.updateSubscriber(subscriber);
        return ResponseEntity.ok(updatedSubscriber);
    }
}

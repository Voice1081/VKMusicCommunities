package ru.fiasco.vkmusic.service;

import ru.fiasco.vkmusic.domain.Record;
import ru.fiasco.vkmusic.domain.repository.RecordRepository;
import ru.fiasco.vkmusic.service.util.PreworkChecker;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordService.class);

    private RecordRepository recordRepository;
    private PreworkChecker preworkChecker;

    public RecordService(RecordRepository recordRepository, PreworkChecker preworkChecker) {
        this.recordRepository = recordRepository;
        this.preworkChecker = preworkChecker;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Record saveNewRecord(final Record record) {
        preworkChecker.throwIfRecordAlreadyExists(record.getId());
        Record newRecord = recordRepository.save(record);
        LOGGER.info("Created new Record object {}", newRecord);
        return newRecord;
    }

    public List<Record> getAll() {
        return recordRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Record updateRecord(final Record record) throws NotFoundException {
        preworkChecker.throwIfRecordDoesNotExists(record.getId());
        Record savedRecord = recordRepository.save(record);
        LOGGER.info("Updated community object {}", record);
        return savedRecord;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteRecord(UUID id) throws NotFoundException {
        preworkChecker.throwIfRecordDoesNotExists(id);
        recordRepository.deleteById(id);
        LOGGER.info("Deleted record object with id {}", id);
    }

    public List<Record> getAllByLink(String link) {
        return recordRepository.getAllByLink(link);
    }
}

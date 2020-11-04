package com.example.demo.service;

import com.example.demo.domain.Record;
import com.example.demo.domain.TestTable;
import com.example.demo.domain.repository.RecordRepository;
import com.example.demo.domain.repository.TestTableRepository;
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

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Record saveNewRecord(final Record record) {
        LOGGER.info("Created new Record object {}", record);
        return recordRepository.save(record);
    }

    public List<Record> getAll() {
        return recordRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Record updateRecord(final Record record) throws NotFoundException {
        if(recordRepository.existsById(record.getId())) {
            return recordRepository.save(record);
        }
        else{
            throw new NotFoundException(String.format("Not found record with id %s", record.getId()));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteRecord(UUID id) throws NotFoundException {
        if(recordRepository.existsById(id)) {
            recordRepository.deleteById(id);
        }
        else{
            throw new NotFoundException(String.format("Not found record with id %s", id));
        }
    }

    public List<Record> getAllByLink(String link){
        return recordRepository.getAllByLink(link);
    }
}

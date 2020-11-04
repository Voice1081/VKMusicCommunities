package com.example.demo.service;

import com.example.demo.domain.TestTable;
import com.example.demo.domain.repository.TestTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TestTableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestTableService.class);

    private TestTableRepository testTableRepository;

    public TestTableService(TestTableRepository testTableRepository) {
        this.testTableRepository = testTableRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TestTable saveNewCocker(final String name) {
        TestTable testTable = new TestTable(UUID.randomUUID(), name);
        LOGGER.info("Created new TestTable object {}", testTable);
        return testTableRepository.save(testTable);
    }

    public List<TestTable> getAllByName(final String name) {
        return testTableRepository.getByName(name);
    }
}

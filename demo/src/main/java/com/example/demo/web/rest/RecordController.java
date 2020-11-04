package com.example.demo.web.rest;

import com.example.demo.domain.Record;
import com.example.demo.domain.TestTable;
import com.example.demo.dto.RecordDTO;
import com.example.demo.dto.SimpleDTO;
import com.example.demo.service.RecordService;
import com.example.demo.service.TestTableService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class RecordController {
    private RecordService recordService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordController.class);



    @PostMapping(path = "/records")
    public ResponseEntity<Record> saveNewRecord(final @RequestBody Record record) {
        LOGGER.info("Received REST request to save new record {}", record);
        Record savedRecord = recordService.saveNewRecord(record);
        return ResponseEntity.ok(savedRecord);
    }

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @DeleteMapping(path = "/records")
    public ResponseEntity<Void> deleteRecord(final @RequestParam(value = "id") UUID id) throws NotFoundException {
        LOGGER.info("Received REST request to delete record with id {}", id);
        recordService.deleteRecord(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/records/{link}")
    public ResponseEntity<List<Record>> getAllByLink(final @PathVariable(value = "link") String link) {
        LOGGER.info("Received REST request to get all records by link {}", link);
        List<Record> allByLink = recordService.getAllByLink(link);
        return ResponseEntity.ok(allByLink);
    }

    @GetMapping(path = "/records/all")
    public ResponseEntity<List<Record>> getAll(){
        LOGGER.info("Received REST request to get all records");
        List<Record> all = recordService.getAll();
        return ResponseEntity.ok(all);
    }

    @PutMapping(path = "/records")
    public ResponseEntity<Record> updateRecord(final @RequestBody Record record) throws NotFoundException {
        LOGGER.info("Received REST request to update record {}", record);
        Record updatedRecord = recordService.updateRecord(record);
        return ResponseEntity.ok(updatedRecord);
    }
}

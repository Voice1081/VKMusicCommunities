package com.example.demo.web.rest;

import com.example.demo.domain.TestTable;
import com.example.demo.dto.SimpleDTO;
import com.example.demo.service.TestTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class BaseController {
    private TestTableService testTableService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public BaseController(TestTableService testTableService) {
        this.testTableService = testTableService;
    }

    @GetMapping(path = "/say_cock/{cocky_thing}")
    public ResponseEntity<SimpleDTO> sayCock(final @PathVariable(value = "cocky_thing") String cockyThing) {
        LOGGER.info("Received REST request to name {} as cock", cockyThing);
        testTableService.saveNewCocker(cockyThing);
        return ResponseEntity.ok(new SimpleDTO(cockyThing));
    }

    @GetMapping(path = "/say_cock")
    public ResponseEntity<SimpleDTO> sayCockParam(final @RequestParam(value = "cocky_thing") String cockyThing) {
        LOGGER.info("Received REST request to name {} as cock", cockyThing);
        testTableService.saveNewCocker(cockyThing);
        return ResponseEntity.ok(new SimpleDTO(cockyThing));
    }

    @GetMapping(path = "/getAllByName")
    public ResponseEntity<List<TestTable>> getAllByName(final @RequestParam(value = "name") String name) {
        LOGGER.info("Received REST request to name {} as cock", name);
        List<TestTable> allByName = testTableService.getAllByName(name);
        return ResponseEntity.ok(allByName);
    }
}

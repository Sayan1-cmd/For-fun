package com.project.project;

import com.project.project.entity.Box;
import com.project.project.entity.Item;
import com.project.project.interacting.PostRequest;
import com.project.project.service.BoxService;
import com.project.project.service.FrontService;
import com.project.project.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class FrontController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);
    private final BoxService boxService;
    private final ItemService itemService;
    private final FrontService frontService;
    private static final int REQUEST_LENGTH_THRESHOLD = 25;

    public FrontController(BoxService boxService, ItemService itemService, FrontService frontService) {
        this.boxService = boxService;
        this.itemService = itemService;
        this.frontService = frontService;
    }

    @PostMapping(value = "/getIds", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> createInfo(@RequestHeader(name = "Content-Length") int length, @RequestBody PostRequest request) {
        if (REQUEST_LENGTH_THRESHOLD < length) {
            return new ResponseEntity<>(Collections.singletonList(length), HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("createInfo: request=[{}]", request);
        Box box = boxService.getBoxById(request.getBox());
        List<Box> allBoxes = new ArrayList<>();
        allBoxes.add(box);
        frontService.getAllBoxes(box, allBoxes);
        List<Item> items = itemService.getByBoxesAndColor(allBoxes, request.getColor());
        List<Integer> responseIds = items
                .stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseIds, HttpStatus.OK);
    }

    @GetMapping("/rio")
    public ResponseEntity<String> get() {
        return new ResponseEntity<>("Hello!", HttpStatus.OK);
    }


}

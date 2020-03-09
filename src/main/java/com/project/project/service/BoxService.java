package com.project.project.service;

import com.project.project.entity.Box;
import com.project.project.repository.BoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BoxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoxService.class);
    private final BoxRepository boxRepository;

    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @Transactional
    public void saveBoxes(List<Box> boxes) {
        boxRepository.saveAll(boxes);
    }

    public Box getBoxById(Integer boxId) {
        LOGGER.debug("getBoxById: boxId=[{}]", boxId);
        return boxRepository.findById(boxId).orElseThrow(() -> new RuntimeException(String.format("Box with the id=%s was not found in the DataBase", boxId)));
    }
}

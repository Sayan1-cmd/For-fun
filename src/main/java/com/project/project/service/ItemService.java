package com.project.project.service;

import com.project.project.entity.Box;
import com.project.project.entity.Item;
import com.project.project.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Transactional
    public void saveItems(List<Item> items) {
        itemRepository.saveAll(items);
    }
    public List<Item> getByBoxesAndColor(List<Box> containerIds, String color) {
        LOGGER.info("getByBoxes: ids=[{}]", containerIds);
        return itemRepository.getByBoxInAndColor(containerIds, color);
    }
}

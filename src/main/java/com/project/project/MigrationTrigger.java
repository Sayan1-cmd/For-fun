package com.project.project;

import com.project.project.model.Box;
import com.project.project.model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MigrationTrigger {

    public void setContainedIn(Box box, List<Integer> ids, List<Item> allItems) {
        List<Box> boxes = box.getBoxes();
        List<Item> items = box.getItems();
        if (items != null) {
            for (Item item : items) {
                item.setContainedIn(box.getBoxId());
                allItems.add(item);
            }
        }
        if (boxes != null) {
            for (Box entity : boxes) {
                entity.setContainedIn(box.getBoxId());
                ids.add(box.getBoxId());
                setContainedIn(entity, ids, allItems);
            }
        }
    }

    public List<com.project.project.entity.Box> init(Box box) {
        List<com.project.project.entity.Box> all = new ArrayList<>();
        com.project.project.entity.Box entity = new com.project.project.entity.Box();
        entity.setId(box.getBoxId());
        all.add(entity);
        fillTheBoxContainer(box, all);
        return all;
    }

    private void fillTheBoxContainer(Box input, List<com.project.project.entity.Box> all) {
        List<Box> boxes = input.getBoxes();
        if (boxes != null) {
            for (Box model : boxes) {
                com.project.project.entity.Box box = new com.project.project.entity.Box();
                box.setId(model.getBoxId());
                box.setContainedIn(model.getContainedIn());
                all.add(box);
                fillTheBoxContainer(model, all);
            }
        }
    }
}
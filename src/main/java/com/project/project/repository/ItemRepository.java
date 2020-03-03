package com.project.project.repository;

import com.project.project.entity.Box;
import com.project.project.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> getByBoxInAndColor(List<Box> containerIds, String color);
}

package com.project.project.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="Storage")
@XmlAccessorType(XmlAccessType.FIELD)
public class Storage {

    @XmlElement(name = "Box")
    List<Box> boxes;
    @XmlElement(name = "Item")
    List<Item> items;

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

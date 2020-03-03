package com.project.project.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Box {

    @XmlAttribute(name = "id")
    private int boxId;
    @XmlElement(name = "Item")
    private List<Item> items;
    @XmlElement(name = "Box")
    private List<Box> boxes;
    private int containedIn;

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public int getContainedIn() {
        return containedIn;
    }

    public void setContainedIn(int containedIn) {
        this.containedIn = containedIn;
    }

    @Override
    public String toString() {
        return "Box{" +
                "boxId=" + boxId +
                ", items=" + items +
                ", boxes=" + boxes +
                ", containedIn=" + containedIn +
                '}';
    }
}

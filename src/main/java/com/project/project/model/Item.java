package com.project.project.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlAttribute(name = "id")
    private int itemId;
    @XmlAttribute(name = "color")
    private String itemColor;
    private int containedIn;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getContainedIn() {
        return containedIn;
    }

    public void setContainedIn(int containedIn) {
        this.containedIn = containedIn;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemColor='" + itemColor + '\'' +
                ", containedIn=" + containedIn +
                '}';
    }
}

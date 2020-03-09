package com.project.project.model;

import com.project.project.entity.Box;

import java.util.List;

public class XMLParsingResult {

    private List<Box> boxList;
    private List<com.project.project.model.Item> itemList;

    public XMLParsingResult(List<Box> boxList, List<com.project.project.model.Item> itemList){
        this.boxList = boxList;
        this.itemList  = itemList;
    }

    public List<Box> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<Box> boxList) {
        this.boxList = boxList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "XMLParsingResult{"
                + "boxList=" + boxList
                + ", itemList=" + itemList
                + '}';
    }
}

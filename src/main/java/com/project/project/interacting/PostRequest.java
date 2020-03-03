package com.project.project.interacting;

public class PostRequest {

    private Integer box;
    private String color;

    public Integer getBox() {
        return box;
    }

    public void setBox(Integer box) {
        this.box = box;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "box=" + box +
                ", color='" + color + '\'' +
                '}';
    }
}

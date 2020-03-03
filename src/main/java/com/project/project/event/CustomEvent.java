package com.project.project.event;

import com.project.project.model.Box;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class CustomEvent extends ApplicationEvent {

    private List<Box> boxes;

    public CustomEvent(Object source, List<Box> boxes) {
        super(source);
        this.boxes = boxes;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

}

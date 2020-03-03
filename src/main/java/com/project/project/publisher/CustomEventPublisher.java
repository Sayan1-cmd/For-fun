package com.project.project.publisher;

import com.project.project.event.CustomEvent;
import com.project.project.model.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(List<Box> boxes){
            System.out.println("Publishing custom event. ");
    CustomEvent customEvent = new CustomEvent(new Object(), boxes);
        applicationEventPublisher.publishEvent(customEvent);
}
}

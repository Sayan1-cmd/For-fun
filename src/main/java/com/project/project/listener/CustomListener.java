package com.project.project.listener;

import com.project.project.event.CustomEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomListener implements ApplicationListener<CustomEvent> {

        @Override
        public void onApplicationEvent(CustomEvent event) {
            System.out.println("Received spring custom event - " + event.getBoxes());
        }
    }

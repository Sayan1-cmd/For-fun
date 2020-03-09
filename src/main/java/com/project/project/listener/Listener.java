package com.project.project.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Listener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("Context refreshed. Application successfully started");
    }

}

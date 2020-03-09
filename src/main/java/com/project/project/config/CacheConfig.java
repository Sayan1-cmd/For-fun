package com.project.project.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(){
        CaffeineCacheManager cashManager = new CaffeineCacheManager();
        cashManager.setCacheNames(Collections.singletonList("pureCash"));
        cashManager.setAllowNullValues(false);
        return new CaffeineCacheManager();
    }
}

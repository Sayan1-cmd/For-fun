package com.project.project.service;

import com.project.project.entity.ApplicationUser;
import com.project.project.repository.ApplicationUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserService.class);

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public ApplicationUser getUserByUserName(String userName){
        LOGGER.info("getUserByUserName: login=[{}]", userName);
        return applicationUserRepository.findByUserName(userName);
    }

    public void saveUser(ApplicationUser user){
        applicationUserRepository.save(user);
    }
}

package com.project.project.service;

import com.project.project.entity.Email;
import com.project.project.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Transactional
    public Email saveSingle(Email email) {
        LOGGER.info("saveSingle: email=[{}]", email);
        return emailRepository.save(email);
    }
}

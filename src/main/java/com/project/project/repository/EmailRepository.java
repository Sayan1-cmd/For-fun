package com.project.project.repository;

import com.project.project.entity.Email;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<Email, Long> {
}

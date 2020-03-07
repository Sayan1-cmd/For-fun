package com.project.project.repository;

import com.project.project.entity.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {

    ApplicationUser findByUserName(String userName);
}

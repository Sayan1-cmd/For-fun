package com.project.project.repository;


import com.project.project.entity.Box;
import org.springframework.data.repository.CrudRepository;

import javax.jws.Oneway;

public interface BoxRepository extends CrudRepository<Box, Integer> {
}

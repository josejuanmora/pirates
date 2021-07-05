package com.pirates.jpa.repositories;

import com.pirates.jpa.entities.Good;
import org.springframework.data.repository.CrudRepository;

/**
 * The JPA repository for the good entity.
 */
public interface GoodRepository extends CrudRepository<Good, Long> {

}

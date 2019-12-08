package com.stratio.pirates.jpa.repositories;

import com.stratio.pirates.jpa.entities.Good;
import org.springframework.data.repository.CrudRepository;

/**
 * The JPA repository for the good entity.
 */
public interface GoodRepository extends CrudRepository<Good, Long> {

}

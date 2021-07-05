package com.pirates.jpa.repositories;

import com.pirates.jpa.entities.Ship;
import org.springframework.data.repository.CrudRepository;


/**
 * The JPA repository for the ship entity.
 */
public interface ShipRepository extends CrudRepository<Ship, Long> {
}

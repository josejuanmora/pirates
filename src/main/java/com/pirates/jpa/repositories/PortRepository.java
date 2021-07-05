package com.pirates.jpa.repositories;

import com.pirates.jpa.entities.Port;
import org.springframework.data.repository.CrudRepository;

/**
 * The JPA repository for the port entity.
 */
public interface PortRepository extends CrudRepository<Port, Long> {
}

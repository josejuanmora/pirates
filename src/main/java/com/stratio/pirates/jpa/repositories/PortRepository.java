package com.stratio.pirates.jpa.repositories;

import com.stratio.pirates.jpa.entities.Port;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The JPA repository for the port entity.
 */
public interface PortRepository extends CrudRepository<Port, Long> {
}

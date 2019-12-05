package com.stratio.pirates.jpa.repositories;

import com.stratio.pirates.jpa.entities.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * The JPA repository for the event entity.
 */
public interface EventRepository extends CrudRepository<Event, Long> {

}

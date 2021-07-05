package com.pirates.jpa.repositories;

import com.pirates.jpa.entities.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * The JPA repository for the event entity.
 */
public interface EventRepository extends CrudRepository<Event, Long> {

}

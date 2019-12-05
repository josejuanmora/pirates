package com.stratio.pirates.dto;

import com.stratio.pirates.jpa.entities.EventType;
import lombok.Builder;
import lombok.Getter;

/**
 * Contains the information needed to create an event.
 */
@Builder
@Getter
public class CreateEventDTO {

    private EventType eventType;

    private long portId;

    private StockDTO stock;

}

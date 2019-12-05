package com.stratio.pirates.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.stratio.pirates.jpa.entities.EventType;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * Contains the information needed to display an event.
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EventDTO {

    private EventType eventType;

    private PortDTO port;

    private ShipDTO ship;

    private StockDTO stock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date creationDate;
}

package com.pirates.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pirates.jpa.entities.EventType;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

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

    private List<GoodDTO> goods;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date creationDate;
}

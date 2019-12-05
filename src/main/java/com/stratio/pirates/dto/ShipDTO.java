package com.stratio.pirates.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stratio.pirates.jpa.entities.Event;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * Models the DTO for a ship.
 */
@Getter
@Builder
public class ShipDTO {

    private long id;

    private String name;

    private int barrelsOfRum;

    private int goldCoins;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date registrationDate;

    private List<Event> events;
}

package com.pirates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Models the DTO for a ship.
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShipDTO {

    private long id;

    private String name;

    private List<EventDTO> events;
}

package com.pirates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * Models the information for a port.
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PortDTO implements Serializable {

    private long id;

    private String name;

    private List<GoodDTO> goods;

    private List<EventDTO> events;
}

package com.stratio.pirates.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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

    private StockDTO stock;

    private List<EventDTO> events;
}

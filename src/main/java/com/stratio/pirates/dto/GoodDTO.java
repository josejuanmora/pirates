package com.stratio.pirates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stratio.pirates.jpa.entities.GoodType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Models the DTO a good for the ship or port.
 */
@Getter
@Builder
public class GoodDTO {

    private GoodType goodType;

    private int qty;
}

package com.pirates.dto;

import com.pirates.jpa.entities.GoodType;
import lombok.Builder;
import lombok.Getter;

/**
 * Models the DTO a good for the ship or port.
 */
@Getter
@Builder
public class GoodDTO {

    private GoodType goodType;

    private int qty;
}

package com.stratio.pirates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Models the DTO for the stock of a ship or port.
 */
@Getter
@Builder
public class StockDTO {

    private int barrelsOfRum;

    private int goldCoins;
}

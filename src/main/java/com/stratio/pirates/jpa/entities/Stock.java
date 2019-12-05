package com.stratio.pirates.jpa.entities;

import lombok.Builder;
import lombok.Getter;

/**
 * Models the stock of a ship or port.
 */
@Getter
@Builder
public class Stock {

    private int barrelsOfRum;

    private int goldCoins;
}

package com.stratio.pirates.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Models the stock of a ship or port.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Stock {

    private int barrelsOfRum;

    private int goldCoins;
}

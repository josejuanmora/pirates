package com.stratio.pirates;

import com.stratio.pirates.jpa.entities.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Contains some helper methods for the tests.
 */
public class BuilderHelper {

    public static Ship buildShip(final int id, final Event... events) {
        return Ship.builder().id(id).events(Arrays.asList(events)).build();
    }

    public static Event buildEvent(final EventType type, final int portId, final int shipId, final Stock stock) {
        return Event.builder().eventType(type).port(buildPort(portId)).ship(buildShip(shipId)).stock(stock).build();
    }

    public static Port buildPort(final int id, final Event... events) {
        return Port.builder().id(id).events(Arrays.asList(events)).build();
    }

    public static Stock buildStock(final int barrelsOfRum, final int goldCoins) {
        return new Stock(barrelsOfRum, goldCoins);
    }
}

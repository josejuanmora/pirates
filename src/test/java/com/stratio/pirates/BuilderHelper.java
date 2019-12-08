package com.stratio.pirates;

import com.stratio.pirates.jpa.entities.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Contains some helper methods for the tests.
 */
public class BuilderHelper {

    public static Ship buildShip(final int id, final Event... events) {
        return Ship.builder().id(id).events(Arrays.asList(events)).build();
    }

    public static Event buildEvent(final EventType type, final int portId, final int shipId, final List<Good> goods) {
        return Event.builder().eventType(type).port(buildPort(portId)).ship(buildShip(shipId)).goods(goods).build();
    }

    public static Port buildPort(final int id, final Event... events) {
        return Port.builder().id(id).events(Arrays.asList(events)).build();
    }

    public static List<Good> buildGoods(final int barrelsOfRum, final int goldCoins) {
        return Arrays.asList(
                Good.builder().goodType(GoodType.BARRELS_OF_RUM).qty(barrelsOfRum).build(),
                Good.builder().goodType(GoodType.GOLD_COINS).qty(goldCoins).build());
    }
}

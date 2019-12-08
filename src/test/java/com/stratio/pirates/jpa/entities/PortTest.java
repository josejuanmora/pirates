package com.stratio.pirates.jpa.entities;

import static com.stratio.pirates.BuilderHelper.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Tests to check the functionality of the Port class.
 */
public class PortTest {

    @Test
    public void shouldHaveCorrectQty() {
        int blackPearlShip = 1;
        int stJamesPort = 1;

        List<Good> arrivalGoods = buildGoods(3, 4);
        Event arrivalEvent = buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, arrivalGoods);
        List<Good> departureGoods = buildGoods(2, 1);
        Event departureEvent = buildEvent(EventType.DEPARTURE_FROM_PORT, stJamesPort, blackPearlShip, departureGoods);
        Port port = buildPort(stJamesPort, departureEvent, arrivalEvent);

        List<Good> portGoods = port.getGoods();
        Assert.assertNotNull(portGoods);
        Assert.assertEquals(portGoods.size(), 2);
        Assert.assertEquals(getSizeOfGoodType(portGoods, GoodType.BARRELS_OF_RUM), 1);
        Assert.assertEquals(getSizeOfGoodType(portGoods, GoodType.GOLD_COINS), 3);
    }

    private int getSizeOfGoodType(final List<Good> goods, final GoodType goodType) {
        return goods.stream().filter(g -> g.getGoodType().equals(goodType)).mapToInt(g -> g.getQty()).sum();
    }
}

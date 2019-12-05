package com.stratio.pirates.jpa.entities;

import static com.stratio.pirates.BuilderHelper.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Tests to check the functionality of the Port class.
 */
public class PortTest {
    
    @Test
    public void shouldCreateEvent() {
        int blackPearlShip = 1;
        int stJamesPort = 1;

        Stock arrivalStock = buildStock(3, 4);
        Event arrivalEvent = buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, arrivalStock);
        Stock departureStock = buildStock(2, 1);
        Event departureEvent = buildEvent(EventType.DEPARTURE_FROM_PORT, stJamesPort, blackPearlShip, departureStock);
        Port port = buildPort(stJamesPort, departureEvent, arrivalEvent);

        Stock portStock = port.getStock();
        Assert.assertNotNull(portStock);
        Assert.assertEquals(portStock.getBarrelsOfRum(), 1);
        Assert.assertEquals(portStock.getGoldCoins(), 3);
    }
}

package com.stratio.pirates.jpa.entities;

import static com.stratio.pirates.BuilderHelper.*;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Tests to check the functionality of the EventType class.
 */
public class EventTypeTest {

    private int blackPearlShip = 1;
    private int stJamesPort = 1;
    private int beauPort = 2;

    private Stock defaultStock = buildStock(1, 1);

    @Test
    public void shouldCreateEvent() {
        Port port = buildPort(stJamesPort);
        Ship ship = buildShip(blackPearlShip);
        Assert.assertTrue(EventType.ARRIVAL_TO_PORT.isEventAllowed(ship, port, defaultStock));

        Event arrivalEvent = buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, defaultStock);
        port = buildPort(stJamesPort, arrivalEvent);
        ship = buildShip(blackPearlShip, arrivalEvent);
        Assert.assertTrue(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, defaultStock));

        Event departureEvent = buildEvent(EventType.DEPARTURE_FROM_PORT, stJamesPort, blackPearlShip, defaultStock);
        port = buildPort(stJamesPort, departureEvent, arrivalEvent);
        ship = buildShip(blackPearlShip, departureEvent, arrivalEvent);
        Assert.assertTrue(EventType.ARRIVAL_TO_PORT.isEventAllowed(ship, port, defaultStock));
    }

    @Test
    public void ShouldNotCreateEventBecauseOfInvalidStock() {
        Stock stock = buildStock(-1, -1);
        Port port = buildPort(stJamesPort);
        Ship ship = buildShip(blackPearlShip);
        Assert.assertFalse(EventType.ARRIVAL_TO_PORT.isEventAllowed(ship, port, stock));

        Event arrivalEvent = buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, defaultStock);
        stock = buildStock(-1, -1);
        port = buildPort(stJamesPort, arrivalEvent);
        ship = buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, stock));

        stock = buildStock(1, 2);
        port = buildPort(stJamesPort, arrivalEvent);
        ship = buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, stock));

        stock = buildStock(2, 1);
        port = buildPort(stJamesPort, arrivalEvent);
        ship = buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, stock));
    }

    @Test
    public void ShouldNotCreateEventBecauseOfInvalidPort() {
        Event arrivalEvent = buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, defaultStock);
        Port port = buildPort(beauPort);
        Ship ship = buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, defaultStock));
    }

    @Test
    public void ShouldNotCreateEventBecauseOfRepeatedEvent() {
        Event arrivalEvent = buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, defaultStock);
        Port port = buildPort(stJamesPort, arrivalEvent);
        Ship ship = buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.ARRIVAL_TO_PORT.isEventAllowed(ship, port, defaultStock));

        Event departureEvent = buildEvent(EventType.DEPARTURE_FROM_PORT, stJamesPort, blackPearlShip, defaultStock);
        port = buildPort(stJamesPort, departureEvent);
        ship = buildShip(blackPearlShip, departureEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, defaultStock));
    }
}

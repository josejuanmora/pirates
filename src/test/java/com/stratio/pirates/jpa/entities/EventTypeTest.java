package com.stratio.pirates.jpa.entities;

import com.stratio.pirates.jpa.entities.*;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;

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

    private Ship buildShip(final int id, final Event... events) {
        return Ship.builder().id(id).events(Arrays.asList(events)).build();
    }

    private Event buildEvent(final EventType type, final int portId, final int shipId,  final Stock stock) {
        return Event.builder().eventType(type).port(buildPort(portId)).ship(buildShip(shipId)).stock(stock).build();
    }

    private Port buildPort(final int id, final Event... events) {
        return Port.builder().id(id).events(Arrays.asList(events)).build();
    }

    private Stock buildStock(final int barrelsOfRum, final int goldCoins) {
        return new Stock(barrelsOfRum, goldCoins);
    }
}

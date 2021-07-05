package com.pirates.jpa.entities;

import com.pirates.BuilderHelper;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

/**
 * Tests to check the functionality of the EventType class.
 */
public class EventTypeTest {

    private int blackPearlShip = 1;
    private int stJamesPort = 1;
    private int beauPort = 2;

    private List<Good> defaultStock = BuilderHelper.buildGoods(1, 1);

    @Test
    public void shouldCreateEvent() {
        Port port = BuilderHelper.buildPort(stJamesPort);
        Ship ship = BuilderHelper.buildShip(blackPearlShip);
        Assert.assertTrue(EventType.ARRIVAL_TO_PORT.isEventAllowed(ship, port, defaultStock));

        Event arrivalEvent = BuilderHelper.buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, defaultStock);
        port = BuilderHelper.buildPort(stJamesPort, arrivalEvent);
        ship = BuilderHelper.buildShip(blackPearlShip, arrivalEvent);
        Assert.assertTrue(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, defaultStock));

        Event departureEvent = BuilderHelper.buildEvent(EventType.DEPARTURE_FROM_PORT, stJamesPort, blackPearlShip, defaultStock);
        port = BuilderHelper.buildPort(stJamesPort, departureEvent, arrivalEvent);
        ship = BuilderHelper.buildShip(blackPearlShip, departureEvent, arrivalEvent);
        Assert.assertTrue(EventType.ARRIVAL_TO_PORT.isEventAllowed(ship, port, defaultStock));
    }

    @Test
    public void ShouldNotCreateEventBecauseOfInvalidStock() {
        List<Good> goods = BuilderHelper.buildGoods(-1, -1);
        Port port = BuilderHelper.buildPort(stJamesPort);
        Ship ship = BuilderHelper.buildShip(blackPearlShip);
        Assert.assertFalse(EventType.ARRIVAL_TO_PORT.isEventAllowed(ship, port, goods));

        Event arrivalEvent = BuilderHelper.buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, defaultStock);
        goods = BuilderHelper.buildGoods(-1, -1);
        port = BuilderHelper.buildPort(stJamesPort, arrivalEvent);
        ship = BuilderHelper.buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, goods));

        goods = BuilderHelper.buildGoods(1, 2);
        port = BuilderHelper.buildPort(stJamesPort, arrivalEvent);
        ship = BuilderHelper.buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, goods));

        goods = BuilderHelper.buildGoods(2, 1);
        port = BuilderHelper.buildPort(stJamesPort, arrivalEvent);
        ship = BuilderHelper.buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, goods));
    }

    @Test
    public void ShouldNotCreateEventBecauseOfInvalidPort() {
        Event arrivalEvent = BuilderHelper.buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, defaultStock);
        Port port = BuilderHelper.buildPort(beauPort);
        Ship ship = BuilderHelper.buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, defaultStock));
    }

    @Test
    public void ShouldNotCreateEventBecauseOfRepeatedEvent() {
        Event arrivalEvent = BuilderHelper.buildEvent(EventType.ARRIVAL_TO_PORT, stJamesPort, blackPearlShip, defaultStock);
        Port port = BuilderHelper.buildPort(stJamesPort, arrivalEvent);
        Ship ship = BuilderHelper.buildShip(blackPearlShip, arrivalEvent);
        Assert.assertFalse(EventType.ARRIVAL_TO_PORT.isEventAllowed(ship, port, defaultStock));

        Event departureEvent = BuilderHelper.buildEvent(EventType.DEPARTURE_FROM_PORT, stJamesPort, blackPearlShip, defaultStock);
        port = BuilderHelper.buildPort(stJamesPort, departureEvent);
        ship = BuilderHelper.buildShip(blackPearlShip, departureEvent);
        Assert.assertFalse(EventType.DEPARTURE_FROM_PORT.isEventAllowed(ship, port, defaultStock));
    }
}

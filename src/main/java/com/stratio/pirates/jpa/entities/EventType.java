package com.stratio.pirates.jpa.entities;

import lombok.AllArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Enumerates all the possible event types.
 */
@AllArgsConstructor
public enum EventType {
    ARRIVAL_TO_PORT((i -> i), ((s,p) -> s.isOnTheHighSeas()), (s,p) -> true),
    DEPARTURE_FROM_PORT((i -> -i), ((s,p) -> s.isAtPort(p)), (s, p) -> canStockBeDelivered(s, p.getStock()) )
    ;

    private Function<Integer, Integer> stockCalculator;

    private BiFunction<Ship, Port, Boolean> locationValidator;

    private BiFunction<Stock, Port, Boolean> stockValidator;

    /**
     * Returns the proper value for calculating the total stock. In the case
     * of arrival events, the value must be added and in the case of
     * departure events, the value must be substracted.
     * @param value the value
     * @return the correct value
     */
    public int changeValueForStockCalculation(final int value) {
        return stockCalculator.apply(value);
    }

    /**
     * Tests whether the event can be applied to the ship and port.
     * @param ship the ship
     * @param port the port
     * @param stock the stock
     * @return true in such case
     */
    public boolean isEventAllowed(final Ship ship, final Port port, final Stock stock) {
        return locationValidator.apply(ship, port) && stockValidator.apply(stock, port);
    }

    /**
     * Checks whether the port can deliver the stock to the ship. It is check that the
     * value that the ship wants to take is minor to the value of the stock the port has.
     * @param stock the stock of the ship
     * @param portStock the stock of the port
     * @return true in such case
     */
    private static boolean canStockBeDelivered(final Stock stock, final Stock portStock) {
        return isValidStockValue(stock.getBarrelsOfRum(), portStock.getBarrelsOfRum()) &&
                isValidStockValue(stock.getGoldCoins(), portStock.getGoldCoins());
    }

    private static boolean isValidStockValue(final int stockValue, final int portValue) {
        return stockValue>=0 && stockValue<=portValue;
    }
}
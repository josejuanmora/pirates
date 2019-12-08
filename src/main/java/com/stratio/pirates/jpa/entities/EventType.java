package com.stratio.pirates.jpa.entities;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Enumerates all the possible event types.
 */
@AllArgsConstructor
public enum EventType {
    ARRIVAL_TO_PORT((i -> i), ((s,p) -> s.isOnTheHighSeas()), (s,p) -> isQtyValidForArrival(s)),
    DEPARTURE_FROM_PORT((i -> -i), ((s,p) -> s.isAtPort(p)), (s, p) -> isQtyValidForDeparture(s, p.getGoods()) )
    ;

    private Function<Integer, Integer> stockCalculator;

    private BiFunction<Ship, Port, Boolean> locationValidator;

    private BiFunction<List<Good>, Port, Boolean> stockValidator;

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
     * @param goods the goods
     * @return true in such case
     */
    public boolean isEventAllowed(final Ship ship, final Port port, final List<Good> goods) {
        return locationValidator.apply(ship, port) && stockValidator.apply(goods, port);
    }

    /**
     * Checks whether the port can deliver the stock to the ship. It is check that the
     * value that the ship wants to take is minor to the value of the stock the port has.
     * @param goods the stock of the ship
     * @param portGoods the stock of the port
     * @return true in such case
     */
    private static boolean isQtyValidForDeparture(final List<Good> goods, final List<Good> portGoods) {
        return goods.stream().allMatch(g -> isQtyValidForDeparture(g, portGoods));
    }

    private static boolean isQtyValidForDeparture(final Good good, final List<Good> portGoods) {
        boolean result = false;

        if(good.getQty()>0) {
            Optional<Good> portGood =
                    portGoods.stream().filter(pg -> pg.getGoodType().equals(good.getGoodType())).findAny();

            if(portGood.isPresent()) {
                result = good.getQty()>=0 && good.getQty()<=portGood.get().getQty();
            }
        }
        return result;
    }

    private static boolean isQtyValidForArrival(final List<Good> goods) {
        return goods.stream().allMatch(g -> g.getQty()>0);
    }
}
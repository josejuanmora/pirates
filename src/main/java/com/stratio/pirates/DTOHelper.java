package com.stratio.pirates;

import com.stratio.pirates.dto.EventDTO;
import com.stratio.pirates.dto.PortDTO;
import com.stratio.pirates.dto.ShipDTO;
import com.stratio.pirates.dto.StockDTO;
import com.stratio.pirates.jpa.entities.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains some helper methods for building DTOs.
 */
public class DTOHelper {

    public static StockDTO toStockDTO(final Stock stock) {
        return StockDTO.builder().
                barrelsOfRum(stock.getBarrelsOfRum()).
                goldCoins(stock.getGoldCoins()).build();
    }

    public static Date from(final LocalDateTime localDateTime) {
        final ZonedDateTime zdt = localDateTime.atZone(Application.DEFAULT_ZONE_ID);
        return Date.from(zdt.toInstant());
    }
}

package com.stratio.pirates;

import com.stratio.pirates.dto.GoodDTO;
import com.stratio.pirates.jpa.entities.Good;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains some helper methods for building DTOs.
 */
public class DTOHelper {

    public static List<GoodDTO> toGoodDTO(final List<Good> goods) {
        return goods.stream().map(g -> toGoodDTO(g)).collect(Collectors.toList());
    }

    private static GoodDTO toGoodDTO(final Good good) {
        return GoodDTO.builder().goodType(good.getGoodType()).qty(good.getQty()).build();
    }

    public static Date from(final LocalDateTime localDateTime) {
        final ZonedDateTime zdt = localDateTime.atZone(Application.DEFAULT_ZONE_ID);
        return Date.from(zdt.toInstant());
    }
}

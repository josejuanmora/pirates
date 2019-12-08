package com.stratio.pirates.jpa.entities;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a port.
 */
@Entity
@Table(name = "ports")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Port implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Port.class);

    @Id
    @SequenceGenerator(name="seq_ports",sequenceName="seq_ports", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_ports")
    @Column(updatable = false)
    private long id;

    @Column(name="name", nullable = false, updatable = false)
    private String name;

    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "port")
    @OrderBy("id desc")
    private List<Event> events;

    /**
     * Returns the total goods of the port.
     * @return the total goods
     */
    public List<Good> getGoods() {
        Map<GoodType, Integer> aggregated = initAggregatedMap();

        events.stream().forEach(e-> agreggateValuesFromEvent(e, aggregated));
        return aggregated.keySet().stream().
                map(k -> Good.builder().goodType(k).qty(aggregated.get(k)).build()).collect(Collectors.toList());
    }

    private void agreggateValuesFromEvent(final Event event, final Map<GoodType, Integer> aggregated) {
        event.getGoods().stream().forEach(g -> aggregateValueFromEvent(event.getEventType(), g, aggregated));
    }

    private void aggregateValueFromEvent(
        final EventType eventType, final Good good, final Map<GoodType, Integer> aggregated) {

        Integer currentValue = aggregated.get(good.getGoodType());
        aggregated.put(good.getGoodType(), currentValue + eventType.changeValueForStockCalculation(good.getQty()));
    }

    private Map<GoodType, Integer> initAggregatedMap() {
        Map<GoodType, Integer> result = new HashMap<>();
        for(GoodType goodType : GoodType.values()) { result.put(goodType, 0); }
        return result;
    }
}

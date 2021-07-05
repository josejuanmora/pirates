package com.pirates.jpa.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Represents a ship.
 */
@Entity
@Table(name = "ships")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Ship implements Serializable {

    @Id
    @SequenceGenerator(name="seq_ships",sequenceName="seq_ships", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_ships")
    @Column(updatable = false)
    private long id;

    @Column(name="name", nullable = false, updatable = false)
    private String name;

    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ship")
    @OrderBy("id desc")
    private List<Event> events;

    /**
     * Checks whether the ship is on high seas.
     * @return true in such case
     */
    public boolean isOnTheHighSeas() {
        return events.size()==0 || events.stream().findFirst().
                filter(e -> e.getEventType().equals(EventType.DEPARTURE_FROM_PORT)).isPresent();
    }

    /**
     * Checks whether the ship is at the given port.
     * @param port the port
     * @return true in such case
     */
    public boolean isAtPort(final Port port) {
        Optional<Port> currentPort = getCurrentPort();
        return currentPort.isPresent() && currentPort.get().getId() == port.getId();
    }

    /**
     * Retrieves the current port for a ship
     * @return the current port, if any
     */
    public Optional<Port> getCurrentPort() {
        Port result = null;
        if(events.size()>0) {
            Event lastEvent = events.get(0);
            result = (lastEvent.getEventType().equals(EventType.ARRIVAL_TO_PORT)) ?
                    lastEvent.getPort() :
                    null;

        }
        return Optional.ofNullable(result);
    }
}

package com.stratio.pirates.jpa.entities;

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

/**
 * Represents an event.
 */
@Entity
@Table(name = "events")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Event implements Serializable {

    @Id
    @SequenceGenerator(name="seq_events",sequenceName="seq_events", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_events")
    @Column(updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Ship ship;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Port port;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type_id", nullable = false, updatable = false)
    private EventType eventType;

    @AttributeOverrides( {
        @AttributeOverride(name="barrelsOfRum", column = @Column(name="barrelsOfRum") ),
        @AttributeOverride(name="goldCoins", column = @Column(name="goldCoins") )
    })
    private Stock stock;

    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime creationDate;
}

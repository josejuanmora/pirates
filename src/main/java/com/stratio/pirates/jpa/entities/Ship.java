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
import java.util.List;

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

    @Column(name="barrels_of_rum", nullable = false, updatable = false)
    private int barrelsOfRum;

    @Column(name="gold_coins", nullable = false, updatable = false)
    private int goldCoins;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Port currentPort;

    @CreatedDate
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ship")
    @OrderBy("id desc")
    private List<Event> events;
}

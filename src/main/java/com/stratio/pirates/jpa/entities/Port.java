package com.stratio.pirates.jpa.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currentPort")
    @OrderBy("id asc")
    private List<Ship> ships;
}

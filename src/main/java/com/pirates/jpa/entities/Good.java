package com.pirates.jpa.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a good.
 */
@Entity
@Table(name = "goods")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Good implements Serializable {

    @Id
    @SequenceGenerator(name="seq_goods",sequenceName="seq_goods", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_goods")
    @Column(updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Setter
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private GoodType goodType;

    @Column(name="qty", nullable = false, updatable = false)
    private int qty;
}

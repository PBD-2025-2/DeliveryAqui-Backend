package com.example.DeliveryAqui.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "extra")
public class Extra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "flavor")
    private String flavor;

    @Column(name = "size", length = 100)
    private String size;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;


}
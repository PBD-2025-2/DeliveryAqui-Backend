package com.example.DeliveryAqui.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "extra_group_id", nullable = false)
    private ExtraGroup extraGroup;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "min_quantity", nullable = false)
    private Short minQuantity;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "max_quantity", nullable = false)
    private Short maxQuantity;
}
package com.sauliyo15.orders.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean validated;

    @Column(nullable = false)
    private LocalDate creationDate;

    private LocalDate validationDate;
}
package com.category.modal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String image;

    @Column(nullable = false)
    private Long salonId;
}

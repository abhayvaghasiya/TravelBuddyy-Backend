package com.travelbuddy.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, length = 1000)
    private String shortDescription;

    @Column(nullable = false)
    private BigDecimal typicalCost;

    @ElementCollection(targetClass = AgeGroup.class)
    @CollectionTable(name = "destination_age_groups", joinColumns = @JoinColumn(name = "destination_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "age_group")
    private Set<AgeGroup> suitableAgeGroups = new HashSet<>();

    @Column(length = 255)
    private String imageUrl;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attraction> attractions = new HashSet<>();
} 
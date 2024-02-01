package com.aex.platform.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exchanges")
public class Exchange {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_from_id")
    private Country countryFrom;

    @ManyToOne
    @JoinColumn(name = "country_to_id")
    private Country countryTo;

    @Column(name = "trm")
    private Double trm;

    @Column(name = "main")
    private Boolean main;

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Bogota"));
    @Column(name = "updated_at")
    public LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("America/Bogota"));
    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;
    @Column(name = "current")
    private Boolean current;
}

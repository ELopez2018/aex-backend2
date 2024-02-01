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
@Table(name = "trm_settings")
public class TrmSetting {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "greater_than")
    private Double greaterThan;

    @Column(name = "smaller_than")
    private Double smallerThan;

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "updated_at")
    public LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;

    @Column(name = "active")
    private Boolean active;

    //@Column(name = "exchange_id")
    //private Long exchangeId;

    @ManyToOne
    @JoinColumn(name = "exchange_id")
    private Exchange exchange;

}

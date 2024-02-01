package com.aex.platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "municipalities")
public class Municipality {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @ManyToOne
  @JoinColumn(name = "state_id")
  private State state;

  @Column(name ="code")
  private String code;

  @Column(name ="name")
  private String name;
}

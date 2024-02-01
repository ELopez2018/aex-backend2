package com.aex.platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "parishes")
public class Parish {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @ManyToOne
  @JoinColumn(name = "municipality_id")
  private Municipality municipality;

  @Column(name ="code")
  private String code;

  @Column(name ="name")
  private String name;

  @Column(name ="capital")
  private String capital;

}

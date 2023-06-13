package com.afrac.serviceorders.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorizations")
public class Authorizations extends EntityBase {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @NotNull(message = "El campo Description de entrada no debe estar vacio.")
  @Column(name = "description", length = 1000)
  private String option;

  @NotNull(message = "El campo Value de entrada no debe estar vacio.")
  @Column(name = "value")
  private boolean value = false;

}

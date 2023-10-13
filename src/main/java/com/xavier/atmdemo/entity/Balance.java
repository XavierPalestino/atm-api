package com.xavier.atmdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "balance")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Balance implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true)
  private Integer id;

  @Column(name = "atm_code", nullable = false, length = 50)
  private String atmCode;

  @Setter
  @Column(name = "balance", nullable = false, precision = 12, scale = 2)
  private double atmBalance;

  public Balance(final String atmCode, final double atmBalance) {
    this.atmCode = atmCode;
    this.atmBalance = atmBalance;
  }
}

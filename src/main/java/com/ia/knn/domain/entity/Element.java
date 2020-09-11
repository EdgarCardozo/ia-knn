package com.ia.knn.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Element {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name = "x_value")
  private double xValue;
  @Column(name = "y_value")
  private double yValue;
  @Column(name = "x_calculated_value")
  private double xCalculatedValue;
  @Column(name = "y_calculated_value")
  private double yCalculatedValue;
  @Column(name = "result")
  private double result;
}



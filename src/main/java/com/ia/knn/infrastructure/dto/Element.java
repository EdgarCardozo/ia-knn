package com.ia.knn.infrastructure.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Element {

  @NotNull(message = "x axis value is mandatory")
  private double xValue;
  @NotNull(message = "y axis value is mandatory")
  private double yValue;
  @NotNull(message = "The final clase is mandatory")
  private String clase;

}

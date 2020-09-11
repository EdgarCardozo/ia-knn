package com.ia.knn.infrastructure.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DimensionalPosition {
  @NotNull(message = "x axis value is mandatory")
  private double xValue;
  @NotNull(message = "y axis value is mandatory")
  private double yValue;

}

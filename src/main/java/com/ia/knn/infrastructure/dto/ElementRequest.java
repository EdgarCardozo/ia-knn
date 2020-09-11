package com.ia.knn.infrastructure.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ElementRequest extends DimensionalPosition {

  @NotNull(message = "The final result is mandatory")
  private double result;

}

package com.ia.knn.infrastructure.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CalculateRequest extends DimensionalPosition {

  @NotNull(message = "The k value is mandatory.")
  @Min(0)
  @Max(10)
  private int kValue;
}

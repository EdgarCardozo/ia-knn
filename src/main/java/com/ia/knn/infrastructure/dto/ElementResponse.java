package com.ia.knn.infrastructure.dto;

import com.ia.knn.domain.entity.Element;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ElementResponse {

  private double xValue;
  private double yValue;
  private double xCalculatedValue;
  private double yCalculatedValue;
  private double result;

  public static ElementResponse from(Element element) {
    return ElementResponse.builder()
        .xCalculatedValue(element.getXCalculatedValue())
        .yCalculatedValue(element.getYCalculatedValue())
        .xValue(element.getXValue())
        .yValue(element.getYValue())
        .result(element.getResult())
        .build();
  }
}

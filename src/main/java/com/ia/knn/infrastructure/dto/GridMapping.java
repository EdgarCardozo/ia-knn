package com.ia.knn.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GridMapping implements Serializable {

    private static final long serialVersionUID = 8799656478674716638L;

    List<Element> gridElements;
    List<Element> testElements;
    List<Element> trainingElements;
    List<BigDecimal> kFactor;
}

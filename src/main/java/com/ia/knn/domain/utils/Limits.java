package com.ia.knn.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Limits {
    private double xMin;
    private double yMin;
    private double xMax;
    private double yMax;
}

package com.ia.knn.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GridMapping {

    List<Element> gridElements;
    List<Element> testElements;
}

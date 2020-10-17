package com.ia.knn.infrastructure.service;

import com.ia.knn.infrastructure.dto.Element;
import com.ia.knn.infrastructure.dto.GridMapping;

import java.util.List;

public interface KnnService {

  GridMapping buildCalculatedGrid(List<Element> elements, Integer kValue);

  GridMapping buildCalculatedGrid(List<Element> elements, Integer kValue, Integer xDivision, Integer yDivision);

}

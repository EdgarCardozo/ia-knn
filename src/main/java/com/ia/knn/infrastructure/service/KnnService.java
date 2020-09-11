package com.ia.knn.infrastructure.service;

import com.ia.knn.domain.entity.Element;
import java.util.List;

public interface KnnService {
  List<Element> calculateNeighbours(Double xValue, Double yValue, int kValue);
}

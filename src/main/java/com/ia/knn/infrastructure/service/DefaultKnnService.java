package com.ia.knn.infrastructure.service;

import com.ia.knn.domain.calculator.KnnCalculator;
import com.ia.knn.domain.entity.Element;
import com.ia.knn.infrastructure.repository.ElementRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultKnnService implements KnnService {

  private KnnCalculator knnCalculator;
  private ElementRepository elementRepository;

  public DefaultKnnService(
      KnnCalculator knnCalculator,
      ElementRepository elementRepository
  ) {
    this.knnCalculator = knnCalculator;
    this.elementRepository = elementRepository;
  }

  public List<Element> calculateNeighbours(Double xValue, Double yValue, int kValue) {
    List<Element> all = elementRepository.findAll();
    Element element = Element.builder()
        .xValue(xValue)
        .yValue(yValue)
        .build();
    return knnCalculator.getNeighbours(all, element, kValue);
  }
}

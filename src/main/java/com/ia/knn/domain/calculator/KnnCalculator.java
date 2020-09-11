package com.ia.knn.domain.calculator;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import com.ia.knn.domain.utils.ElementDistance;
import com.ia.knn.domain.entity.Element;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class KnnCalculator {

  public List<Element> getNeighbours(List<Element> elementsList, Element element, Integer kValue) {
    List<ElementDistance> elementDistance = new ArrayList<>();

    elementsList.forEach(candidate ->
        elementDistance.add(ElementDistance.builder()
            .distance(calculateDistances(candidate,element))
            .element(candidate).build()));

    return getKFirstResults(
            elementDistance.parallelStream()
              .sorted(Comparator.comparing(ElementDistance::getDistance))
              .collect(Collectors.toList()),
            kValue);
  }

  private List<Element> getKFirstResults(List<ElementDistance> distances, Integer kValue) {
    List<Element> firstResults = new ArrayList<>();
    for (int i = 0; i < kValue || distances.size() < kValue; i++) {
      firstResults.add(distances.get(i).getElement());
    }
    return firstResults;
  }

  private double calculateDistances(Element elementI, Element elementJ) {
    return sqrt(pow((elementI.getXValue() - elementJ.getXValue()),2) +
        pow(elementI.getYValue() - elementJ.getYValue(),2));
  }

}

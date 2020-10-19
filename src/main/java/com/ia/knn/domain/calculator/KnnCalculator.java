package com.ia.knn.domain.calculator;

import com.ia.knn.domain.utils.ElementDistance;
import com.ia.knn.infrastructure.dto.Element;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Service
public class KnnCalculator {

  /**
   * Principal method to calculate the K-nearest neighbours.
   *
   * @param elementsList All the neighbours used to filter by distance.
   * @param element      Element to find its neighbours.
   * @param kValue       Number of neighbours.
   * @return A list with the K-nearest neighbours.
   */
  public String calculateNeighbours(List<Element> elementsList, Element element, Integer kValue) {
    List<ElementDistance> elementDistance = elementsList.stream()
            .map(candidate ->
              ElementDistance.builder()
                  .distance(calculateDistances(candidate, element))
                  .element(candidate).build())
            .collect(Collectors.toList());

    return selectBestFitClass(getKFirstResults(elementDistance, kValue));
  }

  private String selectBestFitClass(List<Element> kFirstResults) {
    List<String> classes = kFirstResults.stream().map(Element::getClase).collect(Collectors.toList());
    return classes.stream()
            .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Comparator.comparing(Map.Entry::getValue))
            .orElseThrow(RuntimeException::new)
            .getKey();
  }

  /**
   * Method that returns the k first elements of a given list.
   *
   * @param distances List of ordered elements to
   * @param kValue    Number of elements wanted.
   * @return List of the first k elements.
   */
  private List<Element> getKFirstResults(List<ElementDistance> distances, Integer kValue) {
    return distances.parallelStream()
            .sorted(Comparator.comparing(ElementDistance::getDistance))
            .limit(kValue)
            .map(ElementDistance::getElement)
            .collect(Collectors.toList());
  }

  /**
   * Method that calculates a 2 dimension distance between two elements.
   *
   * @param elementI First element.
   * @param elementJ Second element.
   * @return The distance.
   */
  private double calculateDistances(Element elementI, Element elementJ) {
    return sqrt(pow((elementI.getX() - elementJ.getX()), 2) +
        pow(elementI.getY() - elementJ.getY(), 2));
  }

}

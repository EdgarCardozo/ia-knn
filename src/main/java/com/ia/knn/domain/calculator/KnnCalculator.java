package com.ia.knn.domain.calculator;

import com.ia.knn.domain.utils.ElementDistance;
import com.ia.knn.infrastructure.dto.Element;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
   * @return The best fit class as String.
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
    List<ElementDistance> elementDistances = distances.stream()
            .sorted(Comparator.comparing(ElementDistance::getDistance)).collect(Collectors.toList()).subList(0, kValue);
    // This part check if the following neighbours has the same distance as the last one. If it have, then add to the list.
    for (int i = kValue; i < distances.size(); i++) {
      if (elementDistances.get(kValue - 1).getDistance() == (distances.get(kValue - 1).getDistance())) {
        elementDistances.add(distances.get(i));
      } else
        break;
    }

    return distances.stream()
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

package com.ia.knn.infrastructure.service;

import com.ia.knn.domain.calculator.KnnCalculator;
import com.ia.knn.domain.utils.Limits;
import com.ia.knn.infrastructure.dto.Element;
import com.ia.knn.infrastructure.dto.GridMapping;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class DefaultKnnService implements KnnService {

  private static final int DEFAULT_GRID_DIVISION = 200;
  private static final double PERCENTAGE = 0.8;
  private KnnCalculator knnCalculator;

  public DefaultKnnService(
      KnnCalculator knnCalculator
  ) {
    this.knnCalculator = knnCalculator;
  }

  /**
   *  Default grid Knn Calculator.
   * @param elements complete data set to calculate the grid.
   * @param kValue numbers of neighbours to consider.
   * @return GridMapping that is used to build the graphic.
   */
  public GridMapping  buildCalculatedGrid(List<Element> elements, Integer kValue) {
    // Mix List
    Collections.shuffle(elements);

    // Divides into training elements and testing elements
    int divisor = (int) (elements.size() * PERCENTAGE);
    List<Element> trainElements = elements.subList(0, divisor);
    List<Element> testElements = elements.subList(divisor, elements.size());

    // Obtains grid limits
    Limits limits = getLimits(elements);

    // Calculates grid with class type
    List<Element> gridCalculated = knnGrid(buildGrid(limits), trainElements, kValue);

    return GridMapping.builder()
            .gridElements(gridCalculated)
            .testElements(testElements)
            .build();
  }

  /** Overload.
   *  Custom Grid Builder.
   *
   * @param elements complete data set to calculate the grid.
   * @param kValue numbers of neighbours to consider.
   * @param xDivision Number of division on x axis.
   * @param yDivision Number of division on y axis.
   * @return GridMapping that is used to build the graphic.
   */
  public GridMapping buildCalculatedGrid(List<Element> elements, Integer kValue, Integer xDivision, Integer yDivision) {
    // Mix List
    Collections.shuffle(elements);

    // Divides into training elements and testing elements
    List<Element> trainElements = elements.subList(0, Integer.parseInt(String.valueOf(elements.size() * PERCENTAGE)));
    List<Element> testElements = elements.subList(Integer.parseInt(String.valueOf(elements.size() * PERCENTAGE)), elements.size());

    // Obtains grid limits
    Limits limits = getLimits(elements);

    // Calculates grid with class type
    List<Element> gridCalculated = knnGrid(buildGrid(limits, xDivision, yDivision), trainElements, kValue);

    return GridMapping.builder()
            .gridElements(gridCalculated)
            .testElements(testElements)
            .build();
  }

  /**
   * Given the 2 dimension grid, the training elements, and numbers of neighbours it returns the grid to be drawn.
   * @param gridElements 2 dimension Grid.
   * @param trainElements Training data.
   * @param kValue number of neighbours.
   * @return Calculated Grid.
   */
  private List<Element> knnGrid(List<Element> gridElements, List<Element> trainElements, Integer kValue) {
    return gridElements.stream()
            .map(element ->
                    selectBestFitNeighbour(knnCalculator.getNeighbours(trainElements, element, kValue)))
            .collect(Collectors.toList());

  }

  /**
   * Given List of elements Neighbours, it selects the best fit Neighbour.
   *
   * @param neighbours List of neighbours.
   * @return Best Fit Neighbour.
   */
  private Element selectBestFitNeighbour(List<Element> neighbours) {
    return neighbours.get(0);
  }

  /**
   * Default 2 dimension builder.
   *
   * @param limits limits of the grid.
   * @return 2 dimension Grid without class type.
   */
  private List<Element> buildGrid(Limits limits) {
    List<Element> gridElements = new ArrayList<>();
    double hForX = (limits.getXMax() - limits.getXMin()) / DEFAULT_GRID_DIVISION;
    double hForY = (limits.getXMax() - limits.getXMin()) / DEFAULT_GRID_DIVISION;

    for (int i = 0; i < DEFAULT_GRID_DIVISION; i++) {
      double xIncrement = limits.getXMin() + i * hForX;

      for (int j = 0; j < DEFAULT_GRID_DIVISION; j++) {
        Element element = Element.builder()
                .xValue(xIncrement)
                .yValue(limits.getYMin() + j * hForY)
                .build();
        gridElements.add(element);
      }
    }
    return gridElements;
  }

  /** Overload.
   * 2 dimension builder given number of x & y divisions.
   *
   * @param limits limits of the grid.
   * @param xDivision Number of x divisions.
   * @param yDivision Number of y divisions.
   * @return 2 dimension Grid without class type.
   */
  private List<Element> buildGrid(Limits limits, Integer xDivision, Integer yDivision) {
    List<Element> gridElements = new ArrayList<>();

    double hForX = (limits.getXMax() - limits.getXMin()) / xDivision;
    double hForY = (limits.getYMax() - limits.getYMin()) / yDivision;

    for (int i = 0; i < xDivision; i++) {
      double xIncrement = limits.getXMin() + i * hForX;

      for (int j = 0; j < yDivision; j++) {
        Element element = Element.builder()
                .xValue(xIncrement)
                .yValue(limits.getYMin() + j * hForY)
                .build();
        gridElements.add(element);
      }

    }
    return gridElements;
  }

  /**
   * Calculates the 2 dimension limits of the data set.
   *
   * @param elements data set.
   * @return 2 dimension Limits.
   */
  private Limits getLimits(List<Element> elements) {
    double xMin = elements.stream()
            .min(Comparator.comparing(Element::getXValue)).orElseThrow(RuntimeException::new)
            .getXValue();
    double yMin = elements.stream()
            .min(Comparator.comparing(Element::getXValue)).orElseThrow(RuntimeException::new)
            .getYValue();
    double xMax = elements.stream()
            .max(Comparator.comparing(Element::getXValue)).orElseThrow(RuntimeException::new)
            .getXValue();
    double yMax = elements.stream()
            .max(Comparator.comparing(Element::getXValue)).orElseThrow(RuntimeException::new)
            .getYValue();
    return Limits.builder()
            .xMin(xMin)
            .yMin(yMin)
            .xMax(xMax)
            .yMax(yMax)
            .build();
  }
}

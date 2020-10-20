package com.ia.knn.infrastructure.service;

import com.ia.knn.domain.calculator.KnnCalculator;
import com.ia.knn.domain.utils.Limits;
import com.ia.knn.infrastructure.dto.Element;
import com.ia.knn.infrastructure.dto.GridMapping;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
   * @param dataSet complete data set to calculate the grid.
   * @param kValue numbers of neighbours to consider.
   * @return GridMapping that is used to build the graphic.
   */
  public GridMapping buildGrid(List<Element> dataSet, Integer kValue) {
    // Obtains grid limits
    Limits limits = getLimits(dataSet);
    List<Element> grid = buildGrid(limits);

    return calculateGrid(dataSet, grid, kValue);
  }

  /** Overload.
   *  Custom Grid Builder.
   *
   * @param dataSet complete data set to calculate the grid.
   * @param kValue numbers of neighbours to consider.
   * @param xDivision Number of division on x axis.
   * @param yDivision Number of division on y axis.
   * @return GridMapping that is used to build the graphic.
   */
  public GridMapping buildGrid(List<Element> dataSet, Integer kValue, Integer xDivision, Integer yDivision) {
    // Obtains grid limits
    Limits limits = getLimits(dataSet);
    List<Element> grid = buildGrid(limits, xDivision, yDivision);

    return calculateGrid(dataSet, grid, kValue);
  }

  private GridMapping calculateGrid(List<Element> dataSet, List<Element> grid, Integer kValue) {
    // Mix List
    Collections.shuffle(dataSet);

    // Divides into training elements and testing elements
    int splitIndex = (int) (dataSet.size() * PERCENTAGE);
    List<Element> trainElements = dataSet.subList(0, splitIndex);
    List<Element> testElements = dataSet.subList(splitIndex, dataSet.size());

    // Calculates grid with class type
    List<Element> trainedGrid = trainGrid(grid, trainElements, kValue);

    return GridMapping.builder()
            .gridElements(trainedGrid)
            .testElements(testElements)
            .kFactor(validatePrediction(trainElements, testElements, kValue))
            .build();
  }

  /**
   *  This method validates the accuracy of the prediction.
   *
   * @param trainElements The prediction of the algorithm.
   * @param testElements The test set to validate the algorithm.
   * @param kValue number of neighbours.
   * @return The factor asserted_elements/number_of_test_elements
   */
  private long validatePrediction(List<Element> trainElements, List<Element> testElements, Integer kValue) {
    long assertedTest =
            testElements.parallelStream()
            .filter(e -> knnCalculator.calculateNeighbours(trainElements, e, kValue).equals(e.getClase())).count();
    return assertedTest / testElements.size();
  }

  /**
   * Given the 2 dimension grid, the training elements, and numbers of neighbours it returns the grid to be drawn.
   * @param gridElements 2 dimension Grid.
   * @param trainElements Training data.
   * @param kValue number of neighbours.
   * @return Calculated Grid.
   */
  private List<Element> trainGrid(List<Element> gridElements, List<Element> trainElements, Integer kValue) {
    for (Element element : gridElements) {
      element.setClase(knnCalculator.calculateNeighbours(trainElements, element, kValue));
    }
    return gridElements;
  }

  /**
   * Default 2 dimension builder.
   *
   * @param limits limits of the grid.
   * @return 2 dimension Grid without class type.
   */
  private List<Element> buildGrid(Limits limits) {
    return buildGrid(limits, DEFAULT_GRID_DIVISION, DEFAULT_GRID_DIVISION);
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

    for (int i = 0; i <= xDivision; i++) {
      double xIncrement = limits.getXMin() + i * hForX;
      for (int j = 0; j <= yDivision; j++) {
        Element element = Element.builder()
                .x(xIncrement)
                .y(limits.getYMin() + j * hForY)
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
            .min(Comparator.comparing(Element::getX)).orElseThrow(RuntimeException::new)
            .getX();
    double yMin = elements.stream()
            .min(Comparator.comparing(Element::getY)).orElseThrow(RuntimeException::new)
            .getY();
    double xMax = elements.stream()
            .max(Comparator.comparing(Element::getX)).orElseThrow(RuntimeException::new)
            .getX();
    double yMax = elements.stream()
            .max(Comparator.comparing(Element::getY)).orElseThrow(RuntimeException::new)
            .getY();

    return Limits.builder()
            .xMin(xMin)
            .yMin(yMin)
            .xMax(xMax)
            .yMax(yMax)
            .build();
  }
}

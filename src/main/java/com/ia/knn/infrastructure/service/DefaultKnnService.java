package com.ia.knn.infrastructure.service;

import com.ia.knn.domain.calculator.KnnCalculator;
import com.ia.knn.domain.utils.Limits;
import com.ia.knn.infrastructure.dto.DataSet;
import com.ia.knn.infrastructure.dto.Element;
import com.ia.knn.infrastructure.dto.GridMapping;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

  /**
   * Grid drawer. This method receives a Data Set with the data set and de test elements to re-calculate the grid Mapping.
   * @param dataSet
   * @param kValue
   * @param xDivision
   * @param yDivision
   * @return
   */
  public GridMapping drawGrid(DataSet dataSet, Integer kValue, Integer xDivision, Integer yDivision) {
    Limits limits = getLimits(dataSet.getDataSet());
    List<Element> grid = buildGrid(limits, xDivision, yDivision);
    return calculateGrid(dataSet, grid, kValue);
  }

  /**
   * Overload. Calculates the grid given a training elements, grid and K Value.
   * @param trainingElements used to train new Grid.
   * @param grid Grid with the elements to calculates its Class values.
   * @param kValue number of neighbours.
   * @return GridMapping.
   */
  private GridMapping calculateGrid(List<Element> trainingElements, List<Element> grid, Integer kValue) {
    // Mix List
    Collections.shuffle(trainingElements);

    // Divides into training elements and testing elements
    int splitIndex = (int) (trainingElements.size() * PERCENTAGE);
    List<Element> trainElements = trainingElements.subList(0, splitIndex);
    List<Element> testElements = trainingElements.subList(splitIndex, trainingElements.size());

    // Calculates grid with class type
    List<Element> trainedGrid = trainGrid(grid, trainElements, kValue);

    return GridMapping.builder()
            .gridElements(trainedGrid)
            .testElements(testElements)
            .kFactor(buildAccuracies(trainElements, testElements))
            .build();
  }

  /**
   * Overload. Calculates the grid given a Data Set, grid and K Value.
   * @param dataSet used to train new Grid.
   * @param grid Grid with the elements to calculates its Class values.
   * @param kValue number of neighbours.
   * @return GridMapping.
   */
  private GridMapping calculateGrid(DataSet dataSet, List<Element> grid, Integer kValue) {
    List<Element> trainElements = dataSet.getDataSet().stream()
            .filter(element -> !dataSet.getTestElements().contains(element))
            .collect(Collectors.toList());
    // Calculates grid with class type
    List<Element> trainedGrid = trainGrid(grid, trainElements, kValue);

    return GridMapping.builder()
            .gridElements(trainedGrid)
            .testElements(dataSet.getTestElements())
            .kFactor(Collections.singletonList(calculateAccuracy(trainElements, dataSet.getTestElements(), kValue)))
            .build();
  }

  /**
   * The aim of this method is to calculate the accuracies
   * for the test elements with K neighbours from 1 to the size of the training elements;
   * @param trainElements Training elements.
   * @param testElements Test elements.
   * @return List of Accuracies.
   */
  private List<BigDecimal> buildAccuracies(List<Element> trainElements, List<Element> testElements) {
    List<BigDecimal> kFactors = new ArrayList<>();
    for (int i = 1; i <= trainElements.size(); i++){
      kFactors.add(calculateAccuracy(trainElements, testElements, i));
    }
    return kFactors;
  }

  /**
   *  This method validates the accuracy of the prediction.
   *
   * @param trainElements The prediction of the algorithm.
   * @param testElements The test set to validate the algorithm.
   * @param kValue number of neighbours.
   * @return The factor asserted_elements/number_of_test_elements
   */
  private BigDecimal calculateAccuracy(List<Element> trainElements, List<Element> testElements, Integer kValue) {
    double assertedTest =
            (double) testElements.stream()
                    .filter(e ->
                            knnCalculator.calculateNeighbours(trainElements, e, kValue)
                            .equals(e.getClase()))
                    .count();
    return BigDecimal.valueOf(assertedTest / testElements.size());
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

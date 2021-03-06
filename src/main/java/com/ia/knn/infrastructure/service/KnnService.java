package com.ia.knn.infrastructure.service;

import com.ia.knn.infrastructure.dto.DataSet;
import com.ia.knn.infrastructure.dto.Element;
import com.ia.knn.infrastructure.dto.GridMapping;

import java.util.List;

public interface KnnService {

  GridMapping buildGrid(List<Element> elements, Integer kValue, Integer trainingSize);

  GridMapping buildGrid(List<Element> elements, Integer kValue, Integer xDivision, Integer yDivision, Integer trainingSize);

  GridMapping drawGrid(DataSet grid, Integer kValue, Integer xDivision, Integer yDivision);
}

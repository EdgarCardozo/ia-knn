package com.ia.knn.infrastructure.service;

import com.ia.knn.infrastructure.dto.ElementRequest;
import com.ia.knn.infrastructure.dto.ElementResponse;
import java.util.List;

public interface DataService {

  List<ElementResponse> getAllData();

  List<ElementResponse> bulkDataUpload(List<ElementRequest> elements);

  void deleteAll();
}

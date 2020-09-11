package com.ia.knn.infrastructure.service;

import com.ia.knn.domain.entity.Element;
import com.ia.knn.infrastructure.dto.ElementRequest;
import com.ia.knn.infrastructure.dto.ElementResponse;
import com.ia.knn.infrastructure.repository.ElementRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultDataService implements DataService {

  private ElementRepository elementRepository;

  public DefaultDataService(ElementRepository elementRepository) {
    this.elementRepository = elementRepository;
  }

  public List<ElementResponse> getAllData() {
    List<ElementResponse> responses = new ArrayList<>();
    elementRepository.findAll().forEach(element -> responses.add(ElementResponse.from(element)));
    return responses;
  }

  //TODO add xCalculatedValue and yCalculatedValue generators.
  // Take into account that could be data con database.
  public List<ElementResponse> bulkDataUpload(
      List<ElementRequest> elements) {
    List<Element> elementList = new ArrayList<>();
    for (ElementRequest elementRequest : elements) {
      elementList.add(
        Element.builder()
          .xValue(elementRequest.getXValue())
          .yValue(elementRequest.getYValue())
          .result((elementRequest.getResult()))
          .build());
    }
    List<ElementResponse> responses = new ArrayList<>();
    elementRepository.saveAll(elementList)
        .forEach(element -> responses.add(ElementResponse.from(element)));
    return responses;
  }
}

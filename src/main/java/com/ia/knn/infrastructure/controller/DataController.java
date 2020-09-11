package com.ia.knn.infrastructure.controller;

import com.ia.knn.domain.entity.Element;
import com.ia.knn.infrastructure.dto.ElementRequest;
import com.ia.knn.infrastructure.dto.ElementResponse;
import com.ia.knn.infrastructure.service.DataService;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/data")
public class DataController {

  private DataService defaultDataService;

  public DataController(DataService defaultDataService) {
    this.defaultDataService = defaultDataService;
  }

  @GetMapping("/all")
  public HttpEntity<List<ElementResponse>> getAllData(){
      return ResponseEntity.ok(defaultDataService.getAllData());
  }

  @PostMapping("/bulk-upload")
  public List<ElementResponse> bulkDataUpload(
      @RequestBody List<ElementRequest> elements
  ) {
    return defaultDataService.bulkDataUpload(elements);
  }
}

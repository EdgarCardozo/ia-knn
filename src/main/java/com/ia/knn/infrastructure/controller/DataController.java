package com.ia.knn.infrastructure.controller;

import com.ia.knn.infrastructure.dto.ElementRequest;
import com.ia.knn.infrastructure.dto.ElementResponse;
import com.ia.knn.infrastructure.service.DataService;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/data")
public class DataController {

  private DataService defaultDataService;

  public DataController(DataService defaultDataService) {
    this.defaultDataService = defaultDataService;
  }

  @GetMapping("/all")
  public HttpEntity<List<ElementResponse>> getAllData() {
    return ResponseEntity.ok(defaultDataService.getAllData());
  }

  @PostMapping("/bulk-upload")
  public List<ElementResponse> bulkDataUpload(
      @RequestBody List<ElementRequest> elements
  ) {
    return defaultDataService.bulkDataUpload(elements);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> bulkDeleting() {
    defaultDataService.deleteAll();
    return ResponseEntity.ok("{\"message\": \"Successfully deleted!\"}");
  }
}

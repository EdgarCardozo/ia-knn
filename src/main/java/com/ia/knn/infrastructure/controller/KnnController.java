package com.ia.knn.infrastructure.controller;

import com.ia.knn.domain.entity.Element;
import com.ia.knn.infrastructure.service.KnnService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "knn")
@Validated
public class KnnController {

  private KnnService knnService;

  public KnnController(KnnService knnService) {
    this.knnService = knnService;
  }

  @GetMapping(value = "/calculate")
  public HttpEntity<List<Element>> calculateNeighbours(
      @RequestParam @Min(0) Double xValue,
      @RequestParam @Min(0) Double yValue,
      @RequestParam @Min(1) Integer kValue
  ) {
    return ResponseEntity.ok(knnService.calculateNeighbours(xValue,
        yValue,
        kValue));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<Map<String, String>> handleConstraintViolationException(
      ConstraintViolationException e) {

    Map<String, String> errorMsg = new HashMap<>();
    errorMsg.put("status", "404");
    errorMsg.put("message", "Bad Request");
    e.getConstraintViolations()
        .forEach(error -> errorMsg.put(error.getPropertyPath().toString(), e.getMessage()));

    return new ResponseEntity<>(errorMsg,
        HttpStatus.BAD_REQUEST);
  }
}

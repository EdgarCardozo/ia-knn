package com.ia.knn.infrastructure.controller;

import com.ia.knn.infrastructure.dto.DataSet;
import com.ia.knn.infrastructure.dto.Element;
import com.ia.knn.infrastructure.dto.GridMapping;
import com.ia.knn.infrastructure.service.KnnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/knn")
@Validated
public class KnnController {

  private KnnService knnService;

  public KnnController(KnnService knnService) {
    this.knnService = knnService;
  }

  @PostMapping(value = "/calculate-grid/default")
  public ResponseEntity<GridMapping> calculateGrid(
          @RequestParam @Min(1) Integer kValue,
          @RequestParam @Max(99) @Min(1) Integer trainingSize,
          @RequestBody @NotNull List<Element> gridElements
  ) {
    return ResponseEntity.ok(knnService.buildGrid(
            gridElements,
            kValue,
            trainingSize));
  }

  @PostMapping(value = "/calculate-grid")
  public ResponseEntity<GridMapping> calculateGrid(
          @RequestParam @Min(1) Integer kValue,
          @RequestParam @Min(1) Integer xDivision,
          @RequestParam @Min(1) Integer yDivision,
          @RequestParam @Max(99) @Min(1) Integer trainingSize,
          @RequestBody @NotNull List<Element> gridElements
  ) {
    return ResponseEntity.ok(knnService.buildGrid(
            gridElements,
            kValue,
            xDivision,
            yDivision,
            trainingSize));
  }

  @PostMapping(value = "/draw-grid")
  public ResponseEntity<GridMapping> drawGrid(
          @RequestParam @Min(1) Integer kValue,
          @RequestParam @Min(1) Integer xDivision,
          @RequestParam @Min(1) Integer yDivision,
          @RequestBody @NotNull DataSet grid
  ) {
    return ResponseEntity.ok(knnService.drawGrid(
            grid,
            kValue,
            xDivision,
            yDivision
    ));
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

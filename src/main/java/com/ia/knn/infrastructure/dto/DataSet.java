package com.ia.knn.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DataSet implements Serializable {

    private static final long serialVersionUID = 8799656478674716638L;

    List<Element> dataSet;
    List<Element> testElements;
}

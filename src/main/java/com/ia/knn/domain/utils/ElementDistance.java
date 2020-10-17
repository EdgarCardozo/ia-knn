package com.ia.knn.domain.utils;

import com.ia.knn.infrastructure.dto.Element;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElementDistance {

  private int id;
  private double distance;
  private Element element;

}

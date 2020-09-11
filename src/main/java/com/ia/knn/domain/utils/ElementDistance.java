package com.ia.knn.domain.utils;

import com.ia.knn.domain.entity.Element;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElementDistance implements Comparable<ElementDistance> {
  private int id;
  private double distance;
  private Element element;

  @Override
  public int compareTo(ElementDistance o) {
    return (this.distance<o.getDistance()) ? 1 : -1;
  }
}

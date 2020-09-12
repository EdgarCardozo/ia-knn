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

  /** Method that compares distances between the actual object and the given one.
   * If the given is bigger then returns a positive number. Else it returns an negative number.
   *  The positive value indicates that the given element has a longer distance.
   *  This comparison is intended to be used in a sort method.
   * @param o Element to compare with.
   * @return Positive if the given value is longer. Negative if not.
   */
  @Override
  public int compareTo(ElementDistance o) {
    return (this.distance<o.getDistance()) ? 1 : -1;
  }
}

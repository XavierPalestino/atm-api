package com.xavier.atmdemo.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class AtmDTO {

  @Setter private double balanceInAtm;

  private Map<Double, Integer> billingsToRetrieve;

  public static AtmDTO build(
      final double balanceInAtm, final Map<Double, Integer> billingsToRetrieve) {
    return AtmDTO.builder()
        .balanceInAtm(balanceInAtm)
        .billingsToRetrieve(billingsToRetrieve)
        .build();
  }
}

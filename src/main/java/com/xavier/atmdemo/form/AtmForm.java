package com.xavier.atmdemo.form;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObjectField;

@Data
public class AtmForm implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @ApiObjectField private Double moneyToWithdraw;

  @ApiObjectField private String atmCode;
}

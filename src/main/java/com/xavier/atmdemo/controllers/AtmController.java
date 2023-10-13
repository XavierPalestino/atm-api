package com.xavier.atmdemo.controllers;

import com.xavier.atmdemo.dto.AtmDTO;
import com.xavier.atmdemo.form.AtmForm;
import com.xavier.atmdemo.service.WithdrawService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm")
@RequiredArgsConstructor
public class AtmController {

  private final WithdrawService withdrawService;

  @GetMapping
  public ResponseEntity<AtmDTO> withdrawCash(@RequestBody @Valid final AtmForm atmForm) {
    return ResponseEntity.ok(withdrawService.withdraw(atmForm));
  }
}

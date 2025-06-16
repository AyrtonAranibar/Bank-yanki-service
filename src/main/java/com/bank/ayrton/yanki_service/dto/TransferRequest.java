package com.bank.ayrton.yanki_service.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String fromPhone;
    private String toPhone;
    private Double amount;
}
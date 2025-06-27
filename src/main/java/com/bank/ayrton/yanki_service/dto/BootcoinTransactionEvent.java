package com.bank.ayrton.yanki_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootcoinTransactionEvent {
    private String transactionId;
    private String buyerWalletId;
    private String sellerWalletId;
    private Double amount;
    private TransferMethod transferMethod;
}
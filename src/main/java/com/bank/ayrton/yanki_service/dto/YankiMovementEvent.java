package com.bank.ayrton.yanki_service.dto;

import lombok.Data;

@Data
public class YankiMovementEvent  {
    private String fromCard;   // ID del producto origen
    private String toCard;     // ID del producto destino
    private Double amount;     // Monto de la transferencia
}
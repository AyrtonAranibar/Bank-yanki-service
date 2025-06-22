package com.bank.ayrton.yanki_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //para q JsonDeserializer instancie la clase
public class YankiMovementEvent {
    private String fromCard;
    private String toCard;
    private Double amount;
}
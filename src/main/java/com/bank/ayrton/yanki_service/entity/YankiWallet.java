package com.bank.ayrton.yanki_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "yanki_wallets")
@Data
public class YankiWallet {
    @Id
    private String id;
    private String documentType; // DNI, CEX, Pasaporte

    @Indexed(unique = true)
    private String documentNumber;

    @Indexed(unique = true)
    private String phoneNumber;

    private String imei;

    @Indexed(unique = true)
    private String email;

    private Double balance = 0.0;
    private String cardNumber; // id del producto en product service

}

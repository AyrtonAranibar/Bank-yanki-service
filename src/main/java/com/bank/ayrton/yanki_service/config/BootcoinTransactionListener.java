package com.bank.ayrton.yanki_service.config;

import com.bank.ayrton.bootcoin_service.dto.BootcoinTransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BootcoinTransactionListener {

    @KafkaListener(topics = "yanki-transactions", groupId = "yanki-group")
    public void handleBootcoinTransaction(BootcoinTransactionEvent event) {
        log.info("[YANKI] Recibida transacción BootCoin: {}", event);
        log.info("Comprador (buyerWalletId): {}", event.getBuyerWalletId());
        log.info("Vendedor (sellerWalletId): {}", event.getSellerWalletId());
    }
}
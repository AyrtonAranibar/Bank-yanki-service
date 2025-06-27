package com.bank.ayrton.yanki_service.api.yanki;

import com.bank.ayrton.yanki_service.dto.TransferRequest;
import com.bank.ayrton.yanki_service.dto.YankiWalletDto;
import com.bank.ayrton.yanki_service.entity.YankiWallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface YankiWalletService {

    Mono<YankiWalletDto> createWallet(YankiWalletDto dto);

    Flux<YankiWalletDto> getAllWallets();
    Mono<Void> transfer(TransferRequest request); //transferencia entre monederos
    Mono<YankiWalletDto> linkCard(String phoneNumber, String cardNumber); //para asociar tarjeta de debito a  unn numero

    Mono<YankiWalletDto> findById(String id);
    Mono<YankiWalletDto> findByCardNumber(String cardNumber);
}
package com.bank.ayrton.yanki_service.api.yanki;

import com.bank.ayrton.yanki_service.entity.YankiWallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface YankiWalletRepository extends ReactiveMongoRepository<YankiWallet, String> {
    Mono<YankiWallet> findByPhoneNumber(String phoneNumber);
    Mono<YankiWallet> findByDocumentNumber(String documentNumber);
    Mono<YankiWallet> findByEmail(String email);
}
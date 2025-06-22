package com.bank.ayrton.yanki_service.controller;

import com.bank.ayrton.yanki_service.api.yanki.YankiWalletService;
import com.bank.ayrton.yanki_service.dto.LinkCardRequest;
import com.bank.ayrton.yanki_service.dto.TransferRequest;
import com.bank.ayrton.yanki_service.dto.YankiWalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/yanki")
@RequiredArgsConstructor
public class YankiWalletController {

    private final YankiWalletService service;

    @PostMapping
    public Mono<ResponseEntity<YankiWalletDto>> create(@RequestBody YankiWalletDto dto) {
        return service.createWallet(dto)
                .map(wallet -> ResponseEntity.status(HttpStatus.CREATED).body(wallet))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build()));
    }

    @GetMapping
    public Flux<YankiWalletDto> getAll() {
        return service.getAllWallets();
    }

    @PostMapping("/transfer")
    public Mono<ResponseEntity<String>> transfer(@RequestBody TransferRequest request) {
        return service.transfer(request)
                .thenReturn(ResponseEntity.ok("Transferencia realizada con éxito"))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(error.getMessage())));
    }

    @PutMapping("/link-card")
    public Mono<ResponseEntity<YankiWalletDto>> linkCard(@RequestBody LinkCardRequest request) {
        return service.linkCard(request.getPhoneNumber(), request.getCardNumber())
                .map(ResponseEntity::ok)
                .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));
    }
}
package com.bank.ayrton.yanki_service.service.yanki_wallet;

import com.bank.ayrton.yanki_service.api.yanki.YankiWalletRepository;
import com.bank.ayrton.yanki_service.api.yanki.YankiWalletService;
import com.bank.ayrton.yanki_service.dto.TransferRequest;
import com.bank.ayrton.yanki_service.dto.YankiMovementEvent;
import com.bank.ayrton.yanki_service.dto.YankiWalletDto;
import com.bank.ayrton.yanki_service.entity.YankiWallet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class YankiWalletServiceImpl implements YankiWalletService {

    private final YankiWalletRepository repository;
    private final KafkaTemplate<String, YankiMovementEvent> kafkaTemplate;

    public YankiWalletServiceImpl(YankiWalletRepository repository,
                                  KafkaTemplate<String, YankiMovementEvent> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Mono<YankiWalletDto> createWallet(YankiWalletDto dto) {
        return repository.findByPhoneNumber(dto.getPhoneNumber())
                .flatMap(existing -> Mono.<YankiWalletDto>error(new RuntimeException("El número ya está registrado")))
                .switchIfEmpty(Mono.defer(() -> {
                    YankiWallet entity = toEntity(dto);
                    return repository.save(entity)
                            .map(this::toDto);
                }));
    }
    @Override
    public Flux<YankiWalletDto> getAllWallets() {
        return repository.findAll()
                .map(this::toDto);
    }

    @Override
    public Mono<Void> transfer(TransferRequest request) {
        return repository.findByPhoneNumber(request.getFromPhone())
                .switchIfEmpty(Mono.error(new RuntimeException("Emisor no encontrado")))
                .flatMap(emitter -> {
                    if (emitter.getCardNumber() == null) {
                        return Mono.error(new RuntimeException("El emisor no tiene tarjeta asociada"));
                    }

                    return repository.findByPhoneNumber(request.getToPhone())
                            .switchIfEmpty(Mono.error(new RuntimeException("Receptor no encontrado")))
                            .flatMap(receiver -> {
                                if (receiver.getCardNumber() == null) {
                                    return Mono.error(new RuntimeException("El receptor no tiene tarjeta asociada"));
                                }

                                // Construir el evento y enviarlo por Kafka
                                YankiMovementEvent event = new YankiMovementEvent();
                                event.setFromCard(emitter.getCardNumber());
                                event.setToCard(receiver.getCardNumber());
                                event.setAmount(request.getAmount());

                                kafkaTemplate.send("yanki-transactions", event);
                                return Mono.empty();
                            });
                });
    }

    @Override
    public Mono<YankiWalletDto> linkCard(String phoneNumber, String cardNumber) {
        return repository.findByPhoneNumber(phoneNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(wallet -> {
                    wallet.setCardNumber(cardNumber);
                    return repository.save(wallet)
                            .map(this::toDto);
                });
    }

    @Override
    public Mono<YankiWalletDto> findById(String id) {
        return repository.findById(id)
                .map(this::toDto);
    }

    @Override
    public Mono<YankiWalletDto> findByCardNumber(String cardNumber) {
        return repository.findByCardNumber(cardNumber)
                .map(this::toDto);
    }

    private YankiWallet toEntity(YankiWalletDto dto) {
        YankiWallet wallet = new YankiWallet();
        wallet.setDocumentType(dto.getDocumentType());
        wallet.setDocumentNumber(dto.getDocumentNumber());
        wallet.setPhoneNumber(dto.getPhoneNumber());
        wallet.setEmail(dto.getEmail());
        wallet.setImei(dto.getImei());
        wallet.setCardNumber(dto.getCardNumber());
        return wallet;
    }

    private YankiWalletDto toDto(YankiWallet wallet) {
        YankiWalletDto dto = new YankiWalletDto();
        dto.setDocumentType(wallet.getDocumentType());
        dto.setDocumentNumber(wallet.getDocumentNumber());
        dto.setPhoneNumber(wallet.getPhoneNumber());
        dto.setEmail(wallet.getEmail());
        dto.setImei(wallet.getImei());
        dto.setCardNumber(wallet.getCardNumber());
        return dto;
    }
}
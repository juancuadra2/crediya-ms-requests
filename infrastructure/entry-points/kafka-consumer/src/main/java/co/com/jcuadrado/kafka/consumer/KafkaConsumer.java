package co.com.jcuadrado.kafka.consumer;

import co.com.jcuadrado.kafka.consumer.dto.ClientDTO;
import co.com.jcuadrado.kafka.consumer.mapper.ClientDTOMapper;
import co.com.jcuadrado.usecase.client.ClientUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ReactiveKafkaConsumerTemplate<String, String> consumerTemplate;
    private final ClientUseCase clientUseCase;
    private final ObjectMapper objectMapper;
    private final ClientDTOMapper clientDTOMapper;

    @EventListener(ApplicationStartedEvent.class)
    public Flux<Object> listenMessages() {
        return consumerTemplate
                .receiveAutoAck()
                .publishOn(Schedulers.newBoundedElastic(
                        Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE,
                        Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE,
                        "kafka"))
                .flatMap(clientReceived -> {
                    try {
                        if (clientReceived.value() == null) {
                            log.warn("Received null value for key {}. Skipping...", clientReceived.key());
                            return Mono.empty();
                        }
                        log.info("Record received {}", clientReceived.value());
                        ClientDTO clientDTO = objectMapper.readValue(clientReceived.value(), ClientDTO.class);
                        log.info("Mapped client {}", clientDTO);
                        if (clientDTO.__deleted() == null || !clientDTO.__deleted()) {
                            return clientUseCase.save(clientDTOMapper.toModel(clientDTO))
                                    .doOnNext(client -> log.info("Client saved {}", client));
                        } else {
                            return clientUseCase.delete(clientDTOMapper.toModel(clientDTO));
                        }
                    } catch (Exception e) {
                        log.error("Error processing record", e);
                    }
                    return Mono.empty();
                })
                .doOnError(error -> log.error("Error processing kafka record", error))
                .retry()
                .repeat();
    }
}

package co.com.jcuadrado.sqs.sender;

import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import co.com.jcuadrado.model.creditrequest.gateways.NotifyCreditRequest;
import co.com.jcuadrado.sqs.sender.config.SQSSenderProperties;
import co.com.jcuadrado.sqs.sender.constant.SQSSenderConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements NotifyCreditRequest {

    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<String> notify(CreditRequestResponse creditRequestResponse) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(creditRequestResponse))
                .flatMap(this::send)
                .onErrorResume(JsonProcessingException.class, e -> {
                    log.error(SQSSenderConstants.ERROR_SERIALIZING, e);
                    return Mono.error(e);
                });
    }

    public Mono<String> send(String message) {
        log.info(SQSSenderConstants.SENDING_MESSAGE, message);
        return Mono.fromCallable(() -> buildRequest(message))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.info(SQSSenderConstants.MESSAGE_SENT, response.messageId()))
                .map(SendMessageResponse::messageId)
                .doOnError(error -> log.error(SQSSenderConstants.ERROR_SENDING_MESSAGE, error));
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }
}

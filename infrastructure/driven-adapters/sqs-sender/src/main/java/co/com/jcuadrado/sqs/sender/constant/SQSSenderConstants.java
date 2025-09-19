package co.com.jcuadrado.sqs.sender.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class SQSSenderConstants {
    public static final String SENDING_MESSAGE = "Sending message to SQS: {}";
    public static final String MESSAGE_SENT = "Message sent {}";
    public static final String ERROR_SENDING_MESSAGE = "Error sending message to SQS";
    public static final String ERROR_SERIALIZING = "Error serializando CreditRequestResponse a JSON";
}


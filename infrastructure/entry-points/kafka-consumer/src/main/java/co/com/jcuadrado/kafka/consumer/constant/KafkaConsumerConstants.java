package co.com.jcuadrado.kafka.consumer.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaConsumerConstants {

    public static final String THREAD_NAME = "kafka";

    public static final String WARN_NULL_VALUE = "Received null value for key {}. Skipping...";
    public static final String INFO_RECORD_RECEIVED = "Record received {}";
    public static final String INFO_MAPPED_CLIENT = "Mapped client {}";
    public static final String INFO_CLIENT_SAVED = "Client saved {}";

    public static final String ERROR_PROCESSING_RECORD = "Error processing record";
    public static final String ERROR_PROCESSING_KAFKA_RECORD = "Error processing kafka record";

}


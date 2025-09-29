package co.com.jcuadrado.kafka.consumer.dto;

import co.com.jcuadrado.kafka.consumer.handler.BigDecimalBase64Deserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ClientDTO(
        UUID id,

        @JsonProperty("document_number")
        String documentNumber,
        String name,

        @JsonProperty("lastname")
        String lastName,
        String email,

        @JsonProperty("base_salary")
        @JsonDeserialize(using = BigDecimalBase64Deserializer.class)
        BigDecimal baseSalary,

        @JsonProperty("__deleted")
        Boolean __deleted
) {
}

package co.com.jcuadrado.kafka.consumer.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Base64;

public class BigDecimalBase64Deserializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String base64 = p.getValueAsString();
        if (base64 == null) return null;
        byte[] decoded = Base64.getDecoder().decode(base64);
        BigInteger unscaled = new BigInteger(decoded);
        return new BigDecimal(unscaled, 2);
    }
}


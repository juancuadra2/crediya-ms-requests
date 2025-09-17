package co.com.jcuadrado.api.constant.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreditRequestValidationConstants {

    // Amount validation constants
    public static final String AMOUNT_MIN_MESSAGE = "El monto debe ser mayor a 0";
    public static final String AMOUNT_NOT_NULL_MESSAGE = "El monto es requerido";
    public static final String AMOUNT_MIN_VALUE = "1";
    
    // Limit date validation constants
    public static final String TERM_NOT_NULL_MESSAGE = "El plazo es requerido";

    // Document number validation constants
    public static final String DOCUMENT_NUMBER_NOT_BLANK_MESSAGE = "El número de documento es requerido";
    public static final String DOCUMENT_NUMBER_PATTERN_MESSAGE = "El número de documento debe tener un formato válido";
    
    // Credit type validation constants
    public static final String CREDIT_TYPE_NOT_BLANK_MESSAGE = "El tipo de crédito es requerido";

    public static final String STATUS_REQUIRED_MESSAGE = "El estado es requerido";

    public static final String ID_REQUIRED_MESSAGE = "El id es requerido";
}

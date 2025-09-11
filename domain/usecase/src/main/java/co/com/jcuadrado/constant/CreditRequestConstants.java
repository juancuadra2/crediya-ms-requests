package co.com.jcuadrado.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor( access = lombok.AccessLevel.PRIVATE)
public class CreditRequestConstants {
    public static final String CREDIT_REQUEST_NOT_NULL = "CreditRequest no puede ser null";
    public static final String AMOUNT_REQUIRED = "Monto es requerido";
    public static final String LIMIT_DATE_REQUIRED = "Fecha de vencimiento es requerido";
    public static final String DOCUMENT_NUMBER_REQUIRED = "Número de documento es requerido";
    public static final String STATUS_REQUIRED = "Status es requerido";
    public static final String CREDIT_TYPE_REQUIRED = "Credit Type es requerido";
}

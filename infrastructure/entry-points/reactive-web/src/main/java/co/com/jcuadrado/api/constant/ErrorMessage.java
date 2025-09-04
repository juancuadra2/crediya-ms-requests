package co.com.jcuadrado.api.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessage {

    public static final String DOCUMENT_NUMBER_REQUIRED = "El número de documento es obligatorio";
    public static final String NAME_REQUIRED = "El nombre es obligatorio";
    public static final String NAME_SIZE = "El nombre debe tener entre 2 y 50 caracteres";
    public static final String LAST_NAME_SIZE = "El apellido debe tener entre 2 y 50 caracteres";
    public static final String EMAIL_REQUIRED = "El correo electrónico es obligatorio";
    public static final String EMAIL_INVALID_FORMAT = "El correo electrónico debe tener un formato válido";
    public static final String PHONE_INVALID_PATTERN = "El teléfono debe tener un formato válido";
    public static final String ADDRESS_MAX_LENGTH = "La dirección no puede exceder 200 caracteres";
    public static final String ROLE_REQUIRED = "El rol es obligatorio";
    public static final String BASE_SALARY_MINIMUM = "El salario debe ser mayor a 0";
    public static final String VALIDATION_FAILED = "Validación fallida";
}


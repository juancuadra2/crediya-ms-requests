package co.com.jcuadrado.api.constant.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexPatterns {

    public static final String DOCUMENT_NUMBER_PATTERN = "^[0-9]{8,12}$";

}

package co.com.jcuadrado.constant;

import lombok.Getter;

@Getter
public enum CreditStatusEnum {
    PENDING("Pendiente de revisión");

    private final String description;

    CreditStatusEnum(String description) {
        this.description = description;
    }
}

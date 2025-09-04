package co.com.jcuadrado.constant;

import lombok.Getter;

@Getter
public enum CreditStatus {
    PENDING("Pendiente de revisión");

    private final String description;

    CreditStatus(String description) {
        this.description = description;
    }
}

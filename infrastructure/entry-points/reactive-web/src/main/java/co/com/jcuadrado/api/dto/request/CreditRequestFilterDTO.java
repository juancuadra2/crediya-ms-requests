package co.com.jcuadrado.api.dto.request;

public record CreditRequestFilterDTO (
        int page,
        int size,
        String filter,
        String status
) {}

package co.com.jcuadrado.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class AuthConstant {
    public static final String INVALID_TOKEN = "Token inválido";
    public static final String ACCESS_DENIED = "No tiene los permisos suficientes para realizar esta acción";
}

package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServerConfigConstants {

    // Server Configuration
    public static final String DEVELOPMENT_SERVER_URL = "http://localhost:8081";
    public static final String DEVELOPMENT_SERVER_DESCRIPTION = "Servidor de desarrollo";

    // Contact Information
    public static final String CONTACT_NAME = "Equipo de Desarrollo Crediya";
    public static final String CONTACT_EMAIL = "desarrollo@crediya.com";

    // License Information
    public static final String LICENSE_NAME = "Apache 2.0";
    public static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";
}
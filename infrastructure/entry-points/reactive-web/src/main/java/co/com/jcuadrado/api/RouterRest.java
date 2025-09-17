package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.EndpointConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(
            UpdateCreditRequestHandler updateCreditRequestHandler,
            CreateCreditRequestHandler createCreditRequestHandler,
            GetCreditRequestHandler getCreditRequestHandler
    ) {
        return route(POST(EndpointConstants.REQUEST_API_PATH).and(accept(MediaType.APPLICATION_JSON)), createCreditRequestHandler::listenSaveRequest)
                .andRoute(GET(EndpointConstants.REQUEST_API_PATH).and(accept(MediaType.APPLICATION_JSON)), getCreditRequestHandler::listenGetRequests)
                .andRoute(PUT(EndpointConstants.REQUEST_API_PATH  + "/{id}").and(accept(MediaType.APPLICATION_JSON)), updateCreditRequestHandler::listenChangeStatusRequest);
    }
}

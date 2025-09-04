package co.com.jcuadrado.usecase.user;

import co.com.jcuadrado.constant.UserConstants;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    void testGetUserByDocumentNumberSuccess() {
        String documentNumber = "123456789";
        User user = User.builder()
                .documentNumber(documentNumber)
                .name("John Doe")
                .email("test@mail.com")
                .build();

        when(userRepository.getUserByDocumentNumber(documentNumber)).thenReturn(Mono.just(user));

        Mono<User> result = userUseCase.getUserByDocumentNumber(documentNumber);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).getUserByDocumentNumber(documentNumber);
    }

    @Test
    void testGetUserByDocumentNumberNotFound() {
        String documentNumber = "123456789";

        when(userRepository.getUserByDocumentNumber(documentNumber)).thenReturn(Mono.empty());

        Mono<User> result = userUseCase.getUserByDocumentNumber(documentNumber);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException exception = (BusinessException) error;
                    assertEquals(UserConstants.USER_NOT_FOUND, exception.getMessage());
                })
                .verify();

        verify(userRepository).getUserByDocumentNumber(documentNumber);
    }

}

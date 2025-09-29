package co.com.jcuadrado.api.dto.response;

import co.com.jcuadrado.api.constant.doc.CreditRequestDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = CreditRequestDtoConstants.PAGE_RESPONSE_DESCRIPTION)
public record PageResponseDTO<T>(
        
        @Schema(description = CreditRequestDtoConstants.PAGE_CONTENT_DESCRIPTION)
        List<T> content,
        
        @Schema(description = CreditRequestDtoConstants.PAGE_NUMBER_DESCRIPTION, 
                example = CreditRequestDtoConstants.PAGE_NUMBER_EXAMPLE)
        int page,
        
        @Schema(description = CreditRequestDtoConstants.PAGE_SIZE_DESCRIPTION, 
                example = CreditRequestDtoConstants.PAGE_SIZE_RESPONSE_EXAMPLE)
        int size,
        
        @Schema(description = CreditRequestDtoConstants.PAGE_TOTAL_ELEMENTS_DESCRIPTION, 
                example = CreditRequestDtoConstants.PAGE_TOTAL_ELEMENTS_EXAMPLE)
        long totalElements,
        
        @Schema(description = CreditRequestDtoConstants.PAGE_TOTAL_PAGES_DESCRIPTION, 
                example = CreditRequestDtoConstants.PAGE_TOTAL_PAGES_EXAMPLE)
        int totalPages
) {
}
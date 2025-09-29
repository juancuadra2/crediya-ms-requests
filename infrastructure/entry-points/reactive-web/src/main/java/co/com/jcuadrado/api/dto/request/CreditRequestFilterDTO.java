package co.com.jcuadrado.api.dto.request;

import co.com.jcuadrado.api.constant.doc.CreditRequestDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = CreditRequestDtoConstants.CREDIT_REQUEST_FILTER_DESCRIPTION)
public record CreditRequestFilterDTO (
        @Schema(description = CreditRequestDtoConstants.PAGE_FILTER_DESCRIPTION,
                example = CreditRequestDtoConstants.PAGE_FILTER_EXAMPLE, 
                defaultValue = CreditRequestDtoConstants.PAGE_DEFAULT_VALUE)
        int page,
        
        @Schema(description = CreditRequestDtoConstants.SIZE_FILTER_DESCRIPTION,
                example = CreditRequestDtoConstants.SIZE_FILTER_EXAMPLE, 
                defaultValue = CreditRequestDtoConstants.SIZE_DEFAULT_VALUE)
        int size,
        
        @Schema(description = CreditRequestDtoConstants.FILTER_SEARCH_DESCRIPTION,
                example = CreditRequestDtoConstants.FILTER_SEARCH_EXAMPLE)
        String filter,
        
        @Schema(description = CreditRequestDtoConstants.STATUS_FILTER_DESCRIPTION,
                example = CreditRequestDtoConstants.STATUS_FILTER_EXAMPLE)
        String status
) {}

package co.com.jcuadrado.r2dbc.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestRepositoryConstants {

    public static final String SAVE_CREDIT_REQUEST_ENTRY = "Saving credit request for document number: {}";
    public static final String SAVE_CREDIT_REQUEST_SAVED = "Credit request saved successfully with id: {}";
    public static final String SAVE_CREDIT_REQUEST_ERROR = "Error saving credit request: {}";

    public static final String FIND_CREDIT_REQUESTS_ENTRY = "Finding credit requests with filter: {}, status: {}, page: {}, size: {}";
    public static final String COUNT_CREDIT_REQUESTS_ERROR = "Error counting credit requests: {}";
    public static final String FIND_CREDIT_REQUESTS_ERROR = "Error finding credit requests: {}";

    public static final String UPDATE_STATUS_ENTRY = "Updating credit request status for id: {}";
    public static final String UPDATE_STATUS_SUCCESS = "Credit request status updated successfully for id: {}";
    public static final String UPDATE_STATUS_ERROR = "Error updating credit request status: {}";
}


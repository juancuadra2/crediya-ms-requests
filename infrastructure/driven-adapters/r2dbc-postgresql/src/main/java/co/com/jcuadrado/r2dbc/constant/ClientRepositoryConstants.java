package co.com.jcuadrado.r2dbc.constant;

public final class ClientRepositoryConstants {

    private ClientRepositoryConstants() {}

    public static final String SAVE_CLIENT_ENTRY = "saveClient - entrada id={}";
    public static final String SAVE_CLIENT_UPDATED = "saveClient - cliente actualizado id={}";
    public static final String SAVE_CLIENT_CREATED = "saveClient - cliente creado id={}";
    public static final String SAVE_CLIENT_ERROR = "saveClient - error al guardar cliente";

    public static final String DELETE_CLIENT_ENTRY = "deleteClient - entrada id={}";
    public static final String DELETE_CLIENT_SUCCESS = "deleteClient - cliente eliminado id={}";
    public static final String DELETE_CLIENT_ERROR = "deleteClient - error al eliminar cliente";
}


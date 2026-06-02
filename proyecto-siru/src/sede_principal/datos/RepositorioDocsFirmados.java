package sede_principal.datos;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsabilidad: Almacenar documentos firmados digitalmente.
 */
public class RepositorioDocsFirmados {
    private final Map<String, String> documentos = new HashMap<>();

    public void guardarDocumento(String idDocumento, String contenido) {
        documentos.put(idDocumento, contenido);
    }

    public String consultarDocumento(String idDocumento) {
        return documentos.getOrDefault(idDocumento, "Documento no encontrado");
    }
}

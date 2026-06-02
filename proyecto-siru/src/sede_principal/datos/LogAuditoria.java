package sede_principal.datos;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsabilidad: Registrar eventos y errores para trazabilidad.
 */
public class LogAuditoria {
    private final List<String> registros = new ArrayList<>();

    public void registrarEvento(String evento) {
        registros.add("EVENTO: " + evento);
    }

    public void registrarError(String error) {
        registros.add("ERROR: " + error);
    }

    public List<String> consultarLog() {
        return registros;
    }
}

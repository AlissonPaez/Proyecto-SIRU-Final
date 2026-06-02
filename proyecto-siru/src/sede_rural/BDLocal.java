package sede_rural;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsabilidad: Almacenar temporalmente datos académicos en la sede rural
 * cuando no hay conexión con la sede principal.
 */
public class BDLocal {
    private final Map<String, String> inscripcionesPendientes = new HashMap<>();

    public BDLocal() {}

    /**
     * Registra una inscripción localmente.
     * @param idEstudiante Identificador del estudiante
     * @param idGrupo Grupo al que se inscribe
     */
    public void registrarInscripcionLocal(String idEstudiante, String idGrupo) {
        inscripcionesPendientes.put(idEstudiante, idGrupo);
    }

    /**
     * Consulta las inscripciones pendientes.
     * @return Mapa con inscripciones locales
     */
    public Map<String, String> consultarPendientes() {
        return inscripcionesPendientes;
    }

    /**
     * Limpia las inscripciones locales una vez sincronizadas.
     */
    public void limpiarPendientes() {
        inscripcionesPendientes.clear();
    }
}

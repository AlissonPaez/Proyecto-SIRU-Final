package sede_rural;

import sede_principal.datos.BDAcademica;

/**
 * Responsabilidad: Sincronizar los datos locales de la sede rural con la sede principal
 * cuando vuelve la conexión.
 */
public class AgenteSincronizacion {
    private final BDLocal bdLocal;
    private final BDAcademica bdCentral;

    public AgenteSincronizacion(BDLocal bdLocal, BDAcademica bdCentral) {
        this.bdLocal = bdLocal;
        this.bdCentral = bdCentral;
    }

    /**
     * Sincroniza las inscripciones locales con la base de datos central.
     */
    public void sincronizar() {
        bdLocal.consultarPendientes().forEach((idEstudiante, idGrupo) -> {
            bdCentral.registrarInscripcion(idEstudiante, idGrupo);
        });
        bdLocal.limpiarPendientes();
    }
}

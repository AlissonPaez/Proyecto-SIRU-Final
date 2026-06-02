package sede_rural;

import sede_principal.datos.BDAcademica;

// Cuando se recupera la conexión, envía los datos registrados localmente
// hacia la BD académica principal y resuelve conflictos de sincronización.
public class AgenteSincronizacion {

    private BDLocal bdLocal;
    private BDAcademica bdAcademica;

    public AgenteSincronizacion(BDLocal bdLocal, BDAcademica bdAcademica) {
        this.bdLocal = bdLocal;
        this.bdAcademica = bdAcademica;
    }
}
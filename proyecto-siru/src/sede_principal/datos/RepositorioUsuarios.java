package sede_principal.datos;

// Aquí irá la implementación real para consultar y gestionar estudiantes, docentes y administrativos.
// Consulta la BDAcademica pero va a guardar únicamente las operaciones relacionadas
// con usuarios: búsqueda por email, verificación de credenciales y gestión de roles.

import sede_principal.datos.BDAcademica;

public class RepositorioUsuarios {
    private BDAcademica bdAcademica;

    public RepositorioUsuarios(BDAcademica bdAcademica) {
        this.bdAcademica = bdAcademica;
    }
}


package sede_principal.negocio;

import sede_principal.datos.BDAcademica;

public class ModuloCalificacionesInasistencias {

    private BDAcademica bdAcademica;
    private GeneradorDocumentosLegales generadorDocumentosLegales;

    public ModuloCalificacionesInasistencias(BDAcademica bdAcademica,
                                             GeneradorDocumentosLegales generadorDocumentosLegales) {
        this.bdAcademica = bdAcademica;
        this.generadorDocumentosLegales = generadorDocumentosLegales;
    }

    // Registra la calificación de un estudiante en el grupo indicado.
    // Solo permite el registro dentro de los plazos definidos por el calendario académico.
    // Retorna error si el plazo está vencido o el docente no tiene asignado ese grupo.
    public String registrarCalificacion(String idDocente, String idEstudiante, String idGrupo) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Registra una inasistencia del estudiante en el grupo y fecha indicados.
    // Retorna error si el docente no tiene asignado ese grupo.
    public String registrarInasistencias(String idDocente, String idEstudiante,
                                         String idGrupo, String fecha) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Consulta y retorna todas las calificaciones de un estudiante en una materia.
    // Solo es accesible por el propio estudiante, su docente o el área administrativa.
    public String consultarCalificaciones(String idEstudiante, String idMateria) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Retorna el listado completo de estudiantes inscritos en el grupo indicado.
    // Solo es accesible por el docente asignado a ese grupo.
    public String consultarListadoGrupo(String idDocente, String idGrupo) {
        throw new UnsupportedOperationException("Por implementar");
    }
}

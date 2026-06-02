package sede_principal.negocio;

import sede_principal.datos.BDAcademica;

public class MotorReglas {

    private BDAcademica bdAcademica;

    public MotorReglas(BDAcademica bdAcademica) {
        this.bdAcademica = bdAcademica;
    }

    // Verifica si el estudiante cumple los prerrequisitos de la materia indicada.
    // Retorna aprobado o rechazado con el motivo en caso de rechazo.
    public String validarPrerequisitos(String idEstudiante, String idMateria) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Verifica si el grupo indicado tiene conflicto de horario con las materias
    // en las que el estudiante ya está inscrito.
    // Retorna válido o el detalle del conflicto encontrado.
    public String validarConflictoHorario(String idEstudiante, String idGrupo) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Consulta y retorna la lista de materias disponibles para el estudiante
    // según su historial académico y los prerrequisitos de cada materia.
    public String consultarMateriasDisponibles(String idEstudiante) {
        throw new UnsupportedOperationException("Por implementar");
    }
}

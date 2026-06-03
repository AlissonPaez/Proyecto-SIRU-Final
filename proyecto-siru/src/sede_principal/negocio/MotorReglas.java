package sede_principal.negocio;

import excepciones.validacion.*;
import excepciones.matricula.*;
import sede_principal.datos.BDAcademica;

public class MotorReglas {

    private final BDAcademica bdAcademica;

    public MotorReglas(BDAcademica bdAcademica) {
        this.bdAcademica = bdAcademica;
    }

   
    // Métodos privados
    

    /**
     * Valida que un parámetro de texto no sea nulo ni vacío.
     *
     * @param valor  Valor a validar.
     * @param nombre Nombre del parámetro, usado en el mensaje de error.
     * @throws IllegalArgumentException Si el valor es nulo o vacío.
     */
    private void validarTexto(String valor, String nombre) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo o vacío");
        }
    }

    /**
     * Verifica que el estudiante exista en la base de datos.
     *
     * @param idEstudiante Identificador del estudiante.
     * @throws EstudianteNoEncontradoException Si el estudiante no existe.
     */
    private void verificarEstudianteExiste(String idEstudiante) {
        if (!bdAcademica.existeEstudiante(idEstudiante)) {
            throw new EstudianteNoEncontradoException(
                "Estudiante no encontrado: " + idEstudiante);
        }
    }

    
    // Métodos públicos


    /**
     * Verifica si el estudiante cumple los prerrequisitos de la materia indicada.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param idMateria    Código de la materia.
     * @return "aprobado" si cumple los prerrequisitos, o el motivo del rechazo.
     * @throws IllegalArgumentException        Si algún parámetro es nulo o vacío.
     * @throws EstudianteNoEncontradoException Si el estudiante no existe.
     */
    public String validarPrerequisitos(String idEstudiante, String idMateria) {
        validarTexto(idEstudiante, "idEstudiante");
        validarTexto(idMateria, "idMateria");
        verificarEstudianteExiste(idEstudiante);
        return bdAcademica.verificarPrerequisitos(idEstudiante, idMateria);
    }

    /**
     * Verifica si el grupo tiene conflicto de horario con materias ya inscritas.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param idGrupo      Identificador del grupo.
     * @return "valido" si no hay conflicto, o el detalle del conflicto encontrado.
     * @throws IllegalArgumentException        Si algún parámetro es nulo o vacío.
     * @throws EstudianteNoEncontradoException Si el estudiante no existe.
     */
    public String validarConflictoHorario(String idEstudiante, String idGrupo) {
        validarTexto(idEstudiante, "idEstudiante");
        validarTexto(idGrupo, "idGrupo");
        verificarEstudianteExiste(idEstudiante);
        return bdAcademica.verificarConflictoHorario(idEstudiante, idGrupo);
    }

    /**
     * Retorna la lista de materias disponibles para el estudiante
     * según su historial académico y los prerrequisitos de cada materia.
     *
     * @param idEstudiante Identificador del estudiante.
     * @return Lista de materias disponibles para el estudiante.
     * @throws IllegalArgumentException        Si el ID es nulo o vacío.
     * @throws EstudianteNoEncontradoException Si el estudiante no existe.
     */
    public String consultarMateriasDisponibles(String idEstudiante) {
        validarTexto(idEstudiante, "idEstudiante");
        verificarEstudianteExiste(idEstudiante);
        return bdAcademica.obtenerMateriasDisponibles(idEstudiante);
    }
}
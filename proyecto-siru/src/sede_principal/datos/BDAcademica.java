package sede_principal.datos;

import excepciones.validacion.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsabilidad: Simular operaciones de consulta y escritura en la base de datos académica.
 */
public class BDAcademica {
    private final Map<String, String> estudiantes = new HashMap<>();

    public BDAcademica() {}

    public String consultarEstudiante(String idEstudiante) {
        if (idEstudiante == null || idEstudiante.isBlank()) {
            throw new DatosInvalidosException("El id del estudiante no puede ser vacío");
        }
        if (!estudiantes.containsKey(idEstudiante)) {
            throw new EstudianteNoEncontradoException("Estudiante no encontrado: " + idEstudiante);
        }
        return estudiantes.get(idEstudiante);
    }

    public void registrarInscripcion(String idEstudiante, String idGrupo) {
        if (idEstudiante == null || idGrupo == null) {
            throw new DatosInvalidosException("Parámetros inválidos");
        }
        estudiantes.put(idEstudiante, idGrupo);
    }

        /**
     * Retorna las calificaciones registradas del grupo indicado.
     *
     * @param idGrupo Identificador del grupo académico.
     * @return Mapa de idEstudiante → calificación registrada.
     * @throws DatosInvalidosException Si idGrupo es nulo o vacío.
     */
    public Map<String, Double> consultarCalificaciones(String idGrupo) {
        if (idGrupo == null || idGrupo.isBlank()) {
            throw new DatosInvalidosException("idGrupo no puede ser nulo o vacío");
        }
        // Simulación: retorna mapa vacío hasta tener persistencia real
        return new HashMap<>();
    }

    /**
     * Verifica si el docente tiene autorización para firmar documentos oficiales.
     *
     * @param idDocente Identificador del docente.
     * @return true si está autorizado, false en caso contrario.
     * @throws DatosInvalidosException Si idDocente es nulo o vacío.
     */
    public boolean esFirmanteAutorizado(String idDocente) {
        if (idDocente == null || idDocente.isBlank()) {
            throw new DatosInvalidosException("idDocente no puede ser nulo o vacío");
        }
        // Simulación: siempre autorizado hasta conectar con persistencia real
        return true;
    }

    /**
     * Consulta los datos académicos del graduando para validar su diploma.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param idPrograma   Código del programa académico.
     * @return Mapa con los campos: nombre, programa, fechaGrado.
     * @throws EstudianteNoEncontradoException Si el estudiante no existe.
     * @throws DatosInvalidosException         Si algún parámetro es nulo o vacío.
     */
    public Map<String, String> consultarDatosGraduando(String idEstudiante, String idPrograma) {
        if (idEstudiante == null || idEstudiante.isBlank()) {
            throw new DatosInvalidosException("idEstudiante no puede ser nulo o vacío");
        }
        if (idPrograma == null || idPrograma.isBlank()) {
            throw new DatosInvalidosException("idPrograma no puede ser nulo o vacío");
        }
        if (!estudiantes.containsKey(idEstudiante)) {
            throw new EstudianteNoEncontradoException("Estudiante no encontrado: " + idEstudiante);
        }
        // Simulación: retorna datos ficticios hasta tener persistencia real
        Map<String, String> datos = new HashMap<>();
        datos.put("nombre", "Nombre Simulado");
        datos.put("programa", idPrograma);
        return datos;
    }

    public boolean existeEstudiante(String idEstudiante) {
    if (idEstudiante == null || idEstudiante.isBlank()) {
        throw new DatosInvalidosException("idEstudiante no puede ser nulo o vacío");
    }
    return estudiantes.containsKey(idEstudiante);
    }

    public String verificarPrerequisitos(String idEstudiante, String idMateria) {
        // Simulación: siempre aprueba hasta tener persistencia real
        return "aprobado";
    }

    public String verificarConflictoHorario(String idEstudiante, String idGrupo) {
        // Simulación: siempre válido hasta tener persistencia real
        return "valido";
    }

    public String obtenerMateriasDisponibles(String idEstudiante) {
        // Simulación: lista vacía hasta tener persistencia real
        return "[]";
    }


    public boolean existePeriodoActivo() { return false; } 

    public void abrirPeriodo(String fechaInicio, String fechaCierre) {}

    public void cerrarPeriodo() {}

    public String consultarEstadoMatricula(String idEstudiante) { return "sin matrícula"; }

    public boolean docenteTieneGrupo(String idDocente, String idGrupo) { return true; }

    public boolean esPlazoVigente(String idGrupo) { return true; }

    public void guardarCalificacion(String idEstudiante, String idGrupo) {}

    public void guardarInasistencia(String idEstudiante, String idGrupo, String fecha) {}

    public String obtenerCalificaciones(String idEstudiante, String idMateria) { return "[]"; }

    public String obtenerListadoGrupo(String idGrupo) { return "[]"; }

    public String obtenerReporteRendimiento(String periodo, String sede) { return "{}"; }

    public String obtenerEstudiantesEnRiesgo(String periodo) { return "[]"; }

    /**
     * Consulta el rol del usuario en el sistema (UNA SIMULACION PORQUE AUN NO HAY UNA BASE DE DATOS REAL).
     *
     * @param idUsuario Identificador del usuario.
     * @return Rol del usuario (ej: "vicerrectoria", "docente", "estudiante").
     * @throws DatosInvalidosException Si idUsuario es nulo o vacío.
     */
    public String consultarRol(String idUsuario) {
        if (idUsuario == null || idUsuario.isBlank()) {
            throw new DatosInvalidosException("idUsuario no puede ser nulo o vacío");
        }
        // Simulación: retorna rol fijo hasta tener persistencia real
        return "vicerrectoria";
    }



}

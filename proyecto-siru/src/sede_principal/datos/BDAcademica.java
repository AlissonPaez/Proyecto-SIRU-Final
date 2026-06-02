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
}

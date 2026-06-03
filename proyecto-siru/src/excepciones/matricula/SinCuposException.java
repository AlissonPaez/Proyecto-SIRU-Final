package excepciones.matricula;


 // Se lanza cuando el grupo en el que el estudiante intenta matricularse no tiene cupos disponibles.

public class SinCuposException extends MatriculaException {

    public SinCuposException(String idGrupo) {
        super("El grupo " + idGrupo + " no tiene cupos disponibles");
    }
}

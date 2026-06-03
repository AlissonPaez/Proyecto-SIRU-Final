package excepciones.matricula;

import excepciones.SiruException;

/**
 * Excepción base para errores relacionados con el proceso de matrícula.
 * Todas las excepciones de matrícula heredan de esta clase.
 */
public class MatriculaException extends SiruException {

    public MatriculaException(String mensaje) {
        super(mensaje);
    }
}

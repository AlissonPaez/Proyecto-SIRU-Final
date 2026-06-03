package excepciones.validacion;

import excepciones.SiruException;

/**
 * Excepción base para errores de validación de datos en el sistema.
 * Se lanza cuando los datos de entrada no cumplen las precondiciones requeridas.
 */
public class ValidacionException extends SiruException {

    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}

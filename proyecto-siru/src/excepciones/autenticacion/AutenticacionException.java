package excepciones.autenticacion;

import excepciones.SiruException;

/**
 * Excepción base para errores relacionados con la autenticación de los usuarios.
 * Todas las excepciones de autenticación heredan de esta clase.
 */
public class AutenticacionException extends SiruException {

    public AutenticacionException(String mensaje) {
        super(mensaje);
    }
}

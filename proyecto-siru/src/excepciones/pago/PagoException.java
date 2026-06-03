package excepciones.pago;

import excepciones.SiruException;

/**
 * Excepción base para errores de pago.
 */
public class PagoException extends SiruException {
    public PagoException(String mensaje) {
        super(mensaje);
    }
}


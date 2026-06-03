package excepciones.pago;

/**
 * Excepción para pagos fallidos.
 */
public class PagoFallidoException extends PagoException {
    public PagoFallidoException(String mensaje) {
        super(mensaje);
    }
}
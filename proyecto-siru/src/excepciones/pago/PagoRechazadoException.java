package excepciones.pago;

/**
 * Excepción base para pagos rechazados
 */
public class PagoRechazadoException extends PagoException {
    public PagoRechazadoException(String mensaje) {
        super(mensaje);
    }
}
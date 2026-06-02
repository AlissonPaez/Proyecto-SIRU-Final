package excepciones.pago;

/**
 * Excepción base para errores de pago.
 */
public class PagoException extends Exception{
    
    public PagoException(String mensaje){
        super(mensaje);
    }
}


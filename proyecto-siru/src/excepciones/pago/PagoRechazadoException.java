package excepciones.pago;

/**
 * Excepción base para pagos rechazados
 */
public class PagoRechazadoException extends Exception{

    public PagoException(String mensaje){
        super(mensaje);
    }
    
}
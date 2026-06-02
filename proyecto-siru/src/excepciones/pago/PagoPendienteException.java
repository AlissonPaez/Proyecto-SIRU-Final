package excepciones.pago;

/**
 * Excepción para pagos pendientes.
 */
public class PagoPendienteException extends PagoException{
    
    public PagoPendienteException(String mensaje){
        super(mensaje);
    }
    
}
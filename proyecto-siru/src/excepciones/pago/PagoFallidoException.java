package excepciones.pago;

/**
 * Excepción para pagos fallidos.
 */
public class PagoFallidoException extends Exception{
    
    public PagoFallidoException(String mensaje){
        super(mensaje);
    }
    
}
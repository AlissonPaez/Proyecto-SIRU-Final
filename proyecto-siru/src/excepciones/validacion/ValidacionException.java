package excepciones.validacion;

/**
 * Excepción base para validaciones.
 */
public class ValidacionException extends Exception{
    
    public ValidacionException(String mensaje){
        super(mensaje);
    }
    
}
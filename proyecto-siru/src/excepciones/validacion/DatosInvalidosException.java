package excepciones.validacion;

/**
 * Excepción para datos inválidos.
 */
public class DatosInvalidosException extends Exception{
    
    public DatosInvalidosException(String mensaje){
        super(mensaje);
    }

}
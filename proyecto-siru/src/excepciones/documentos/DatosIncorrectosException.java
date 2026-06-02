package excepciones.documentos;

/**
 * Excepción para datos incorrectos.
 */
public class DatosIncorrectosException extends Exception{
    
    public DatosIncorrectosException(String mensaje){
        super(mensaje);
    }
}
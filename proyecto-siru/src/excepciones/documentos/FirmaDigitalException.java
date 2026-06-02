package excepciones.documentos;

/**
 * Excepción para firmas digitales.
 */
public class FirmaDigitalException extends Exception{
    
    public FirmaDigitalException(String mensaje){
        super(mensaje);
    }
    
}
package excepciones.documentos;

/**
 * Excepción para firmantes no autorizados.
 */
public class FirmanteNoAutorizadoException extends DocumentoException{
    
    public FirmanteNoAutorizadoException(String mensaje){
        super(mensaje);
    }
    
}
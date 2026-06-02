package excepciones.documentos;

/**
 * Excepción para documentos legales.
 */
public class DocumentoLegalException extends Exception{
    
    public DocumentoLegalException(String mensaje){
        super(mensaje);
    }
    
}
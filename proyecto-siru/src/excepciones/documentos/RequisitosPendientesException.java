package excepciones.documentos;

/**
 * Excepción para requisitos pendientes.
 */
public class RequisitosPendientesException extends DocumentoException{
    
    public RequisitosPendientesException(String mensaje){
        super(mensaje);
    }
    
}

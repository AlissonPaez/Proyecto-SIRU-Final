package excepciones.documentos;

import excepciones.SiruException;

// Excepción para indicar que ha ocurrido un error relacionado con los documentos
public class DocumentoException extends SiruException {
    public DocumentoException(String mensaje) {
        super(mensaje);
    }
}
    

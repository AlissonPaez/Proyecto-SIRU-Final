package excepciones.documentos;

import excepciones.SiruException;

// Excepción para indicar que los datos proporcionados son incorrectos
public class DatosIncorrectosException extends SiruException {
    public DatosIncorrectosException(String mensaje) {
        super(mensaje);
    }
}
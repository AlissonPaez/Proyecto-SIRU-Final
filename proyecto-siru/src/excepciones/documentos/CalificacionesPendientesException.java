package excepciones.documentos;

/**
 * Excepción para calificaciones pendientes.
 */
public class CalificacionesPendientesException extends Exception{
    
    public CalificacionesPendientesException(String mensaje){
        super(mensaje);
    }

}
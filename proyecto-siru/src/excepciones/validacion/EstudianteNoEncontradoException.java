package excepciones.validacion;

/**
 * Excepción para estudiantes no encontrados.
 */
public class EstudianteNoEncontradoException extends ValidacionException{
    
    public EstudianteNoEncontradoException(String mensaje){
        super(mensaje);
    }
    
}
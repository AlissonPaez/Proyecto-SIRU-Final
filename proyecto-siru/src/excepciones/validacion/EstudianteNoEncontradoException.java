package excepciones.validacion;


 // Se lanza cuando se busca un estudiante por ID y no existe ningún registro con ese identificador en el sistema.
 
public class EstudianteNoEncontradoException extends ValidacionException {

    public EstudianteNoEncontradoException(String idEstudiante) {
        super("No existe ningún estudiante registrado con el ID: " + idEstudiante);
    }
}

package excepciones.matricula;


 // Se lanza cuando un estudiante intenta matricularse en una materia sin haber aprobado los prerrequisitos requeridos.
 
public class PrerequisitosInsuficientesException extends MatriculaException {

    public PrerequisitosInsuficientesException(String idMateria) {
        super("El estudiante no cumple los prerrequisitos para matricularse en la materia: " + idMateria);
    }
}

package excepciones.matricula;


  // Se lanza cuando el grupo en el que el estudiante intenta matricularse tiene un conflicto de horario con otra materia ya inscrita.
 
public class ConflictoHorarioException extends MatriculaException {

    public ConflictoHorarioException(String idGrupo) {
        super("El grupo " + idGrupo + " tiene conflicto de horario con una materia ya inscrita");
    }
}

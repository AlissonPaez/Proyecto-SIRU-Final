package excepciones.matricula;


 //Se lanza cuando se intenta abrir un período de matrícula y ya existe uno activo en el sistema.
 
public class PeriodoYaActivoException extends MatriculaException {

    public PeriodoYaActivoException() {
        super("Ya existe un período de matrícula activo. Debe cerrarlo antes de abrir uno nuevo");
    }
}

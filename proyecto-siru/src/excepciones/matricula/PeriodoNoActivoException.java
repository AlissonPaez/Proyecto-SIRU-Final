package excepciones.matricula;


 // Se lanza cuando se intenta realizar una operación de matrícula y no hay un período de matrícula activo en el sistema.
 
public class PeriodoNoActivoException extends MatriculaException {

    public PeriodoNoActivoException() {
        super("No hay un período de matrícula activo en este momento");
    }
    public PeriodoNoActivoException(String mensaje) {
        super(mensaje);
    }
    
}

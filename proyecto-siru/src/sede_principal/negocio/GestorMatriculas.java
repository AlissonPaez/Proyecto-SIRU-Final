package sede_principal.negocio;

import sede_principal.datos.BDAcademica;

public class GestorMatriculas {

    private ModuloPasarelaPagos moduloPasarelaPagos;
    private BDAcademica bdAcademica;
    private MotorReglas motorReglas;

    public GestorMatriculas(ModuloPasarelaPagos moduloPasarelaPagos,
                            BDAcademica bdAcademica,
                            MotorReglas motorReglas) {
        this.moduloPasarelaPagos = moduloPasarelaPagos;
        this.bdAcademica = bdAcademica;
        this.motorReglas = motorReglas;
    }

    // Abre el período de matrícula con las fechas de inicio y cierre indicadas.
    // Retorna error si ya hay un período activo en el sistema.
    public String abrirPeriodo(String fechaInicio, String fechaCierre) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Matricula a un estudiante en el grupo indicado.
    // Valida prerrequisitos y disponibilidad de cupos con MotorReglas antes de proceder.
    // Redirige al módulo de pagos para completar el proceso financiero.
    public String matricularEstudiante(String idEstudiante, String idGrupo) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Consulta el estado actual de matrícula de un estudiante en el período vigente.
    // Retorna si está matriculado, pendiente de pago o sin matrícula.
    public String estadoMatricula(String idEstudiante) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Cierra el período de matrícula activo.
    // Retorna error si no hay ningún período activo para cerrar.
    public String cerrarPeriodo() {
        throw new UnsupportedOperationException("Por implementar");
    }
}

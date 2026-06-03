package sede_principal.negocio;

import excepciones.matricula.*;
import excepciones.validacion.*;
import sede_principal.datos.BDAcademica;

public class GestorMatriculas {

    private static final double MONTO_MATRICULA = 1500000.0;

    private final ModuloPasarelaPagos moduloPasarelaPagos;
    private final BDAcademica bdAcademica;
    private final MotorReglas motorReglas;

    public GestorMatriculas(ModuloPasarelaPagos moduloPasarelaPagos,
                            BDAcademica bdAcademica,
                            MotorReglas motorReglas) {
        this.moduloPasarelaPagos = moduloPasarelaPagos;
        this.bdAcademica = bdAcademica;
        this.motorReglas = motorReglas;
    }

    
    // Métodos privados
    

    /**
     * Valida que un parámetro de texto no sea nulo ni vacío.
     *
     * @param valor  Valor a validar.
     * @param nombre Nombre del parámetro, usado en el mensaje de error.
     * @throws IllegalArgumentException Si el valor es nulo o vacío.
     */
    private void validarTexto(String valor, String nombre) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo o vacío");
        }
    }

    /**
     * Verifica que no haya un período de matrícula activo.
     *
     * @throws PeriodoNoActivoException Si ya hay un período activo.
     */
    private void verificarSinPeriodoActivo() {
        if (bdAcademica.existePeriodoActivo()) {
            throw new MatriculaException("Ya hay un período de matrícula activo");
        }
    }

    /**
     * Verifica que haya un período de matrícula activo.
     *
     * @throws MatriculaException Si no hay período activo.
     */
    private void verificarPeriodoActivo() {
        if (!bdAcademica.existePeriodoActivo()) {
            throw new PeriodoNoActivoException(
                "No hay un período de matrícula activo en este momento");
        }
    }

    /**
     * Verifica que el estudiante cumpla prerrequisitos y no tenga conflicto de horario.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param idGrupo      Identificador del grupo.
     * @throws MatriculaException Si hay conflicto de horario o prerrequisitos insuficientes.
     */
    private void verificarReglasMatricula(String idEstudiante, String idGrupo) {
        String resultadoPrerequisitos = motorReglas.validarPrerequisitos(idEstudiante, idGrupo);
        if (!resultadoPrerequisitos.equals("aprobado")) {
            throw new MatriculaException("Prerrequisitos insuficientes: " + resultadoPrerequisitos);
        }
        String resultadoHorario = motorReglas.validarConflictoHorario(idEstudiante, idGrupo);
        if (!resultadoHorario.equals("valido")) {
            throw new ConflictoHorarioException("Conflicto de horario: " + resultadoHorario);
        }
    }

    
    // Métodos públicos
    
    /** 
     * Abre el período de matrícula con las fechas de inicio y cierre indicadas.
     *
     * @param fechaInicio Fecha de inicio del período.
     * @param fechaCierre Fecha de cierre del período.
     * @return Confirmación de apertura del período.
     * @throws IllegalArgumentException Si algún parámetro es nulo o vacío.
     * @throws MatriculaException       Si ya hay un período activo.
     */
    public String abrirPeriodo(String fechaInicio, String fechaCierre) {
        validarTexto(fechaInicio, "fechaInicio");
        validarTexto(fechaCierre, "fechaCierre");
        verificarSinPeriodoActivo();
        bdAcademica.abrirPeriodo(fechaInicio, fechaCierre);
        return "Período de matrícula abierto: " + fechaInicio + " - " + fechaCierre;
    }

    /**
     * Matricula a un estudiante en el grupo indicado.
     * Valida prerrequisitos y conflictos de horario antes de procesar el pago.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param idGrupo      Identificador del grupo.
     * @return Confirmación de matrícula y pago exitoso.
     * @throws IllegalArgumentException Si algún parámetro es nulo o vacío.
     * @throws PeriodoNoActivoException Si no hay período de matrícula activo.
     * @throws MatriculaException       Si hay conflicto de horario o prerrequisitos insuficientes.
     */
    public String matricularEstudiante(String idEstudiante, String idGrupo) {
        validarTexto(idEstudiante, "idEstudiante");
        validarTexto(idGrupo, "idGrupo");
        verificarPeriodoActivo();
        verificarReglasMatricula(idEstudiante, idGrupo);
        String resultadoPago = moduloPasarelaPagos.procesarPago(idEstudiante, MONTO_MATRICULA);
        bdAcademica.registrarInscripcion(idEstudiante, idGrupo);
        return "Matrícula exitosa | " + resultadoPago;
    }

    /**
     * Consulta el estado actual de matrícula del estudiante en el período vigente.
     *
     * @param idEstudiante Identificador del estudiante.
     * @return Estado de matrícula: matriculado, pendiente de pago o sin matrícula.
     * @throws IllegalArgumentException Si idEstudiante es nulo o vacío.
     */
    public String estadoMatricula(String idEstudiante) {
        validarTexto(idEstudiante, "idEstudiante");
        return bdAcademica.consultarEstadoMatricula(idEstudiante);
    }

    /**
     * Cierra el período de matrícula activo.
     *
     * @return Confirmación de cierre del período.
     * @throws PeriodoNoActivoException Si no hay período activo para cerrar.
     */
    public String cerrarPeriodo() {
        verificarPeriodoActivo();
        bdAcademica.cerrarPeriodo();
        return "Período de matrícula cerrado exitosamente";
    }
}
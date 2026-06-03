package sede_principal.negocio;

import excepciones.autenticacion.*;
import excepciones.validacion.*;
import sede_principal.datos.BDAcademica;

public class ModuloReportes {

    private static final String ROL_VICERRECTORIA = "vicerrectoria";

    private final BDAcademica bdAcademica;
    private final MotorReglas motorReglas;

    public ModuloReportes(BDAcademica bdAcademica, MotorReglas motorReglas) {
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
     * Verifica que el usuario tenga rol de Vicerrectoría Académica.
     *
     * @param idUsuario Identificador del usuario.
     * @throws AutenticacionException Si el usuario no tiene el rol requerido.
     */
    private void verificarRolVicerrectoria(String idUsuario) {
        String rol = bdAcademica.consultarRol(idUsuario);
        if (!ROL_VICERRECTORIA.equals(rol)) {
            throw new AutenticacionException(
                "Acceso denegado: se requiere rol de Vicerrectoría Académica");
        }
    }

    
    // Métodos públicos

    /**
     * Genera un reporte consolidado del rendimiento académico por sede y período.
     * Solo accesible por la Vicerrectoría Académica.
     *
     * @param idUsuario Identificador del usuario que solicita el reporte.
     * @param periodo   Período académico a reportar.
     * @param sede      Sede a reportar.
     * @return Reporte consolidado de rendimiento académico.
     * @throws IllegalArgumentException Si algún parámetro es nulo o vacío.
     * @throws AutenticacionException   Si el usuario no tiene rol de Vicerrectoría.
     */
    public String generarReporteRendimiento(String idUsuario, String periodo, String sede) {
        validarTexto(idUsuario, "idUsuario");
        validarTexto(periodo, "periodo");
        validarTexto(sede, "sede");
        verificarRolVicerrectoria(idUsuario);
        return bdAcademica.obtenerReporteRendimiento(periodo, sede);
    }

    /**
     * Identifica estudiantes en riesgo académico en el período indicado.
     * Un estudiante se considera en riesgo si tiene inasistencias altas o calificaciones bajas.
     * Accesible por la Vicerrectoría y los docentes tutores.
     *
     * @param idUsuario Identificador del usuario que solicita el reporte.
     * @param periodo   Período académico a analizar.
     * @return Lista de estudiantes en riesgo académico.
     * @throws IllegalArgumentException Si algún parámetro es nulo o vacío.
     * @throws AutenticacionException   Si el usuario no tiene rol de Vicerrectoría.
     */
    public String identificacionEstudiantesEnRiesgo(String idUsuario, String periodo) {
        validarTexto(idUsuario, "idUsuario");
        validarTexto(periodo, "periodo");
        verificarRolVicerrectoria(idUsuario);
        return bdAcademica.obtenerEstudiantesEnRiesgo(periodo);
    }
}
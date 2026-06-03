package sede_principal.negocio;

import excepciones.validacion.*;
import excepciones.documentos.*;
import sede_principal.datos.BDAcademica;

public class ModuloCalificacionesInasistencias {

    private final BDAcademica bdAcademica;
    private final GeneradorDocumentosLegales generadorDocumentosLegales;

    public ModuloCalificacionesInasistencias(BDAcademica bdAcademica,
                                             GeneradorDocumentosLegales generadorDocumentosLegales) {
        this.bdAcademica = bdAcademica;
        this.generadorDocumentosLegales = generadorDocumentosLegales;
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
     * Verifica que el docente tenga asignado el grupo indicado.
     *
     * @param idDocente Identificador del docente.
     * @param idGrupo   Identificador del grupo.
     * @throws FirmanteNoAutorizadoException Si el docente no tiene asignado ese grupo.
     */
    private void verificarDocenteAsignado(String idDocente, String idGrupo) {
        if (!bdAcademica.docenteTieneGrupo(idDocente, idGrupo)) {
            throw new FirmanteNoAutorizadoException(
                "El docente " + idDocente + " no tiene asignado el grupo " + idGrupo);
        }
    }

    /**
     * Verifica que el plazo de registro de calificaciones esté vigente.
     *
     * @param idGrupo Identificador del grupo.
     * @throws RequisitorsPendientesException Si el plazo de registro está vencido.
     */
    private void verificarPlazoVigente(String idGrupo) {
        if (!bdAcademica.esPlazoVigente(idGrupo)) {
            throw new RequisitosPendientesException(
                "El plazo de registro de calificaciones para el grupo " + idGrupo + " está vencido");
        }
    }

   
    // Métodos públicos
  

    /**
     * Registra la calificación de un estudiante en el grupo indicado.
     * Solo permite el registro dentro de los plazos del calendario académico.
     *
     * @param idDocente    Identificador del docente.
     * @param idEstudiante Identificador del estudiante.
     * @param idGrupo      Identificador del grupo.
     * @return Confirmación del registro de la calificación.
     * @throws IllegalArgumentException       Si algún parámetro es nulo o vacío.
     * @throws FirmanteNoAutorizadoException  Si el docente no tiene asignado ese grupo.
     * @throws RequisitosPendientesException  Si el plazo de registro está vencido.
     */
    public String registrarCalificacion(String idDocente, String idEstudiante, String idGrupo) {
        validarTexto(idDocente, "idDocente");
        validarTexto(idEstudiante, "idEstudiante");
        validarTexto(idGrupo, "idGrupo");
        verificarDocenteAsignado(idDocente, idGrupo);
        verificarPlazoVigente(idGrupo);
        bdAcademica.guardarCalificacion(idEstudiante, idGrupo);
        return "Calificación registrada para estudiante " + idEstudiante + " en grupo " + idGrupo;
    }

    /**
     * Registra una inasistencia del estudiante en el grupo y fecha indicados.
     *
     * @param idDocente    Identificador del docente.
     * @param idEstudiante Identificador del estudiante.
     * @param idGrupo      Identificador del grupo.
     * @param fecha        Fecha de la inasistencia.
     * @return Confirmación del registro de la inasistencia.
     * @throws IllegalArgumentException      Si algún parámetro es nulo o vacío.
     * @throws FirmanteNoAutorizadoException Si el docente no tiene asignado ese grupo.
     */
    public String registrarInasistencias(String idDocente, String idEstudiante,
                                          String idGrupo, String fecha) {
        validarTexto(idDocente, "idDocente");
        validarTexto(idEstudiante, "idEstudiante");
        validarTexto(idGrupo, "idGrupo");
        validarTexto(fecha, "fecha");
        verificarDocenteAsignado(idDocente, idGrupo);
        bdAcademica.guardarInasistencia(idEstudiante, idGrupo, fecha);
        return "Inasistencia registrada para estudiante " + idEstudiante + " en fecha " + fecha;
    }

    /**
     * Consulta todas las calificaciones de un estudiante en una materia.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param idMateria    Código de la materia.
     * @return Lista de calificaciones del estudiante en la materia.
     * @throws IllegalArgumentException Si algún parámetro es nulo o vacío.
     */
    public String consultarCalificaciones(String idEstudiante, String idMateria) {
        validarTexto(idEstudiante, "idEstudiante");
        validarTexto(idMateria, "idMateria");
        return bdAcademica.obtenerCalificaciones(idEstudiante, idMateria);
    }

    /**
     * Retorna el listado de estudiantes inscritos en el grupo indicado.
     *
     * @param idDocente Identificador del docente.
     * @param idGrupo   Identificador del grupo.
     * @return Listado de estudiantes del grupo.
     * @throws IllegalArgumentException      Si algún parámetro es nulo o vacío.
     * @throws FirmanteNoAutorizadoException Si el docente no tiene asignado ese grupo.
     */
    public String consultarListadoGrupo(String idDocente, String idGrupo) {
        validarTexto(idDocente, "idDocente");
        validarTexto(idGrupo, "idGrupo");
        verificarDocenteAsignado(idDocente, idGrupo);
        return bdAcademica.obtenerListadoGrupo(idGrupo);
    }
}
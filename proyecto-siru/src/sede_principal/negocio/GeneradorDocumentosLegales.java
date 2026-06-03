package sede_principal.negocio;

import excepciones.documentos.*;
import sede_principal.datos.BDAcademica;
import sede_principal.datos.RepositorioDocsFirmados;
import sede_principal.datos.ServicioFirmaDigital;

import java.util.Map;
import java.util.UUID;

public class GeneradorDocumentosLegales {

    private final BDAcademica bdAcademica;
    private final ServicioFirmaDigital servicioFirmaDigital;
    private final RepositorioDocsFirmados repositorioDocsFirmados;

    public GeneradorDocumentosLegales(BDAcademica bdAcademica,
                                      ServicioFirmaDigital servicioFirmaDigital,
                                      RepositorioDocsFirmados repositorioDocsFirmados) {
        this.bdAcademica = bdAcademica;
        this.servicioFirmaDigital = servicioFirmaDigital;
        this.repositorioDocsFirmados = repositorioDocsFirmados;
    }

    // Métodos privados
    
    /**
     * Valida que un parámetro no sea nulo ni vacío.
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
     * Verifica que todas las calificaciones del grupo estén registradas.
     *
     * @param idGrupo Identificador del grupo académico.
     * @throws CalificacionesPendientesException Si alguna calificación falta.
     */
        private void verificarCalificacionesCompletas(String idGrupo) throws CalificacionesPendientesException {
        Map<String, Double> calificaciones = bdAcademica.consultarCalificaciones(idGrupo);
        boolean hayPendientes = calificaciones.values().stream().anyMatch(c -> c == null);
        if (hayPendientes) {
            throw new CalificacionesPendientesException(
                "El grupo " + idGrupo + " tiene calificaciones sin registrar");
        }
    }

    /**
     * Verifica que el docente tenga autorización para firmar documentos oficiales.
     *
     * @param idDocente Identificador del docente.
     * @throws FirmanteNoAutorizadoException Si el docente no está autorizado.
     */
    private void verificarAutorizacionFirmante(String idDocente) {
        if (!bdAcademica.esFirmanteAutorizado(idDocente)) {
            throw new FirmanteNoAutorizadoException(
                "El docente " + idDocente + " no tiene autorización para firmar documentos");
        }
    }

    /**
     * Verifica que los datos del graduando sean consistentes con los registros.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param idPrograma   Código del programa académico.
     * @param fechaGrado   Fecha de grado declarada.
     * @throws DatosIncorrectosException Si hay inconsistencias en los datos.
     */
    private void verificarDatosGraduando(String idEstudiante,
                                          String idPrograma,
                                          String fechaGrado) {
        Map<String, String> datos = bdAcademica.consultarDatosGraduando(idEstudiante, idPrograma);
        boolean programaInconsistente = !idPrograma.equals(datos.get("programa"));
        if (programaInconsistente) {
            throw new DatosIncorrectosException(
                "El programa " + idPrograma + " no corresponde al registrado para el estudiante");
        }
    }

    /**
     * Firma el contenido del documento y lo guarda en el repositorio.
     *
     * @param contenido  Texto del documento a firmar.
     * @param idFirmante Identificador de quien firma.
     * @return Identificador único del documento firmado y guardado.
     * @throws FirmaDigitalException Si el servicio de firma falla.
     */
    private String firmarYGuardar(String contenido, String idFirmante)  {
        String documentoFirmado = servicioFirmaDigital.firmarDocumento(contenido, idFirmante);
        String idDocumento = UUID.randomUUID().toString();
        repositorioDocsFirmados.guardarDocumento(idDocumento, documentoFirmado);
        return idDocumento;
    }

    // Métodos públicos

    /**
     * Genera el acta de notas del grupo con la firma digital del docente encargado.
     * Valida que todas las calificaciones estén registradas y que el docente
     * tenga autorización para firmar antes de emitir el acta.
     *
     * @param idDocente Identificador del docente firmante.
     * @param idGrupo   Identificador del grupo académico.
     * @return Identificador único del acta generada y almacenada.
     * @throws IllegalArgumentException          Si algún parámetro es nulo o vacío.
     * @throws CalificacionesPendientesException Si hay calificaciones sin registrar.
     * @throws FirmanteNoAutorizadoException     Si el docente no está autorizado para firmar.
     * @throws FirmaDigitalException             Si el proceso de firma falla.
     */
    public String generarActaNotas(String idDocente, String idGrupo) throws CalificacionesPendientesException {
        validarTexto(idDocente, "idDocente");
        validarTexto(idGrupo, "idGrupo");
        verificarCalificacionesCompletas(idGrupo);
        verificarAutorizacionFirmante(idDocente);
        String contenido = "Acta de notas | Grupo: " + idGrupo + " | Docente: " + idDocente;
        return firmarYGuardar(contenido, idDocente);
    }

    /**
     * Genera el diploma universitario del estudiante con firma digital.
     * Valida que el nombre, programa y fecha sean consistentes con los
     * registros académicos antes de emitirlo.
     *
     * @param idEstudiante Identificador del estudiante graduando.
     * @param idPrograma   Código del programa académico.
     * @param fechaGrado   Fecha de la ceremonia de grado.
     * @return Identificador único del diploma generado y almacenado.
     * @throws IllegalArgumentException  Si algún parámetro es nulo o vacío.
     * @throws DatosIncorrectosException Si hay inconsistencias en los datos del graduando.
     * @throws FirmaDigitalException     Si el proceso de firma falla.
     */
    public String generarDiploma(String idEstudiante, String idPrograma, String fechaGrado) {
        validarTexto(idEstudiante, "idEstudiante");
        validarTexto(idPrograma, "idPrograma");
        validarTexto(fechaGrado, "fechaGrado");
        verificarDatosGraduando(idEstudiante, idPrograma, fechaGrado);
        String contenido = "Diploma | Estudiante: " + idEstudiante
                         + " | Programa: " + idPrograma
                         + " | Fecha: " + fechaGrado;
        return firmarYGuardar(contenido, idEstudiante);
    }

    /**
     * Verifica la autenticidad e integridad de un diploma ya emitido.
     * Retorna el contenido del diploma si existe y es válido.
     *
     * @param idDiploma Identificador único del diploma a validar.
     * @return Contenido del diploma firmado si es auténtico.
     * @throws IllegalArgumentException  Si idDiploma es nulo o vacío.
     * @throws DocumentoLegalException   Si el diploma no existe o fue alterado.
     */
    public String validarDiploma(String idDiploma) throws DocumentoLegalException {
        validarTexto(idDiploma, "idDiploma");
        String documento = repositorioDocsFirmados.consultarDocumento(idDiploma);
        boolean diplomaNoExiste = documento.equals("Documento no encontrado");
        if (diplomaNoExiste) {
            throw new DocumentoLegalException("El diploma " + idDiploma + " no existe o fue alterado");
        }
        return documento;
    }
}
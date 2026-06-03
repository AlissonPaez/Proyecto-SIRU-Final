package sede_principal.presentacion;

import sede_principal.datos.LogAuditoria;
import sede_principal.datos.RepositorioDocsFirmados;

/**
 * Portal público para verificación y búsqueda de documentos firmados digitalmente.
 * Permite a terceros (empleadores, entidades de gobierno) validar la autenticidad
 * de documentos académicos. Registra toda consulta en el log de auditoría.
 */
public class PortalVerificacionDocumentos {

    private final RepositorioDocsFirmados repositorioDocsFirmados;
    private final LogAuditoria logAuditoria;

    public PortalVerificacionDocumentos(RepositorioDocsFirmados repositorioDocsFirmados,
                                        LogAuditoria logAuditoria) {
        this.repositorioDocsFirmados = repositorioDocsFirmados;
        this.logAuditoria = logAuditoria;
    }
    
    // Métodos privados
   

    /**
     * Valida que un parámetro de texto no sea nulo ni vacío.
     *
     * @param valor  El valor a validar.
     * @param nombre Nombre del parámetro, usado en el mensaje de excepción.
     * @throws IllegalArgumentException Si el valor es nulo o está en blanco.
     */
    private void validarParametroTexto(String valor, String nombre) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo o vacío");
        }
    }

    /**
     * Construye la clave compuesta usada para buscar un documento en el repositorio.
     *
     * @param nombreGraduado Nombre completo del graduado.
     * @param programa       Programa académico cursado.
     * @param fechaGrado     Fecha de grado en formato definido por el repositorio.
     * @return Clave compuesta en formato "nombre|programa|fecha".
     */
    private String construirClaveDocumento(String nombreGraduado, String programa,
                                           String fechaGrado) {
        return nombreGraduado + "|" + programa + "|" + fechaGrado;
    }

    /**
     * Determina si el resultado devuelto por el repositorio indica que el
     * documento no fue encontrado.
     *
     * @param resultado Texto retornado por el repositorio.
     * @return true si el documento no existe en el repositorio.
     */
    private boolean documentoNoEncontrado(String resultado) {
        return resultado.equals("Documento no encontrado");
    }
    
    // Métodos públicos

    /**
     * Verifica la autenticidad de un documento escaneando su código QR.
     * Retorna los datos básicos del documento si es válido, o un mensaje de
     * documento no encontrado o alterado. Registra cada consulta en el log
     * de auditoría.
     *
     * @param codigoQR Código QR escaneado del documento. No puede ser nulo ni vacío.
     * @return Contenido del documento si es auténtico, o mensaje indicando
     *         que no fue encontrado.
     * @throws IllegalArgumentException Si codigoQR es nulo o vacío.
     */
    public String verificarDocumento(String codigoQR) {
        validarParametroTexto(codigoQR, "codigoQR");

        String resultado = repositorioDocsFirmados.consultarDocumento(codigoQR);

        if (documentoNoEncontrado(resultado)) {
            logAuditoria.registrarError("Verificación fallida para QR: " + codigoQR);
            return "Documento no encontrado o alterado";
        }

        logAuditoria.registrarEvento("Verificación exitosa para QR: " + codigoQR);
        return resultado;
    }

    /**
     * Busca un documento por nombre del graduado, programa y fecha de grado.
     * Es accesible públicamente por terceros como empleadores o entidades de gobierno.
     * Registra cada consulta en el log de auditoría.
     *
     * @param nombreGraduado Nombre completo del graduado. No puede ser nulo ni vacío.
     * @param programa       Programa académico del graduado. No puede ser nulo ni vacío.
     * @param fechaGrado     Fecha de grado del graduado. No puede ser nula ni vacía.
     * @return Contenido del documento si existe, o mensaje indicando que no fue encontrado.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo o vacío.
     */
    public String buscarDocumento(String nombreGraduado, String programa,
                                  String fechaGrado) {
        validarParametroTexto(nombreGraduado, "nombreGraduado");
        validarParametroTexto(programa, "programa");
        validarParametroTexto(fechaGrado, "fechaGrado");

        String claveDocumento = construirClaveDocumento(nombreGraduado, programa, fechaGrado);
        String resultado = repositorioDocsFirmados.consultarDocumento(claveDocumento);

        if (documentoNoEncontrado(resultado)) {
            logAuditoria.registrarError("Búsqueda sin resultados: " + claveDocumento);
            return "Documento no encontrado";
        }

        logAuditoria.registrarEvento("Búsqueda exitosa: " + claveDocumento);
        return resultado;
    }
}
package sede_principal.presentacion;

import sede_principal.datos.LogAuditoria;
import sede_principal.datos.RepositorioDocsFirmados;

public class PortalVerificacionDocumentos {

    private RepositorioDocsFirmados repositorioDocsFirmados;
    private LogAuditoria logAuditoria;

    public PortalVerificacionDocumentos(RepositorioDocsFirmados repositorioDocsFirmados,
                                        LogAuditoria logAuditoria) {
        this.repositorioDocsFirmados = repositorioDocsFirmados;
        this.logAuditoria = logAuditoria;
    }

    // Verifica la autenticidad de un documento escaneando su código QR.
    // Retorna los datos básicos del documento si es válido, o mensaje de documento
    // no encontrado o alterado. Registra cada consulta en el log de auditoría.
    public String verificarDocumento(String codigoQR) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Busca un documento por nombre del graduado, programa y fecha de grado.
    // Es accesible públicamente por terceros como empleadores o entidades de gobierno.
    // Registra cada consulta en el log de auditoría.
    public String buscarDocumento(String nombreGraduado, String programa,
                                  String fechaGraduado) {
        throw new UnsupportedOperationException("Por implementar");
    }
}

package sede_principal.negocio;

import sede_principal.datos.BDAcademica;
import sede_principal.datos.RepositorioDocsFirmados;
import sede_principal.datos.ServicioFirmaDigital;

public class GeneradorDocumentosLegales {

    private BDAcademica bdAcademica;
    private ServicioFirmaDigital servicioFirmaDigital;
    private RepositorioDocsFirmados repositorioDocsFirmados;

    public GeneradorDocumentosLegales(BDAcademica bdAcademica,
                                      ServicioFirmaDigital servicioFirmaDigital,
                                      RepositorioDocsFirmados repositorioDocsFirmados) {
        this.bdAcademica = bdAcademica;
        this.servicioFirmaDigital = servicioFirmaDigital;
        this.repositorioDocsFirmados = repositorioDocsFirmados;
    }

    // Genera el acta de notas del grupo indicado con la firma digital de la persona encargada.
    // Valida que todas las calificaciones estén registradas antes de generar el acta.
    // Retorna error si hay calificaciones pendientes o quien firma no tiene autorización.
    public String generarActaNotas(String idDocente, String idGrupo, String idFirmante) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Genera el diploma universitario del estudiante con firma digital.
    // Valida que el nombre, programa y fecha sean correctos antes de emitirlo
    // Retorna error si hay inconsistencias en los datos del graduando.
    public String generarDiploma(String idEstudiante, String idPrograma,
                                 String fechaGrado, String idFirmante) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Verifica la autenticidad e integridad de un diploma ya emitido.
    // Retorna los datos del diploma si es válido, o error si fue alterado o no existe.
    public String validarDiploma(String idDiploma) {
        throw new UnsupportedOperationException("Por implementar");
    }
}


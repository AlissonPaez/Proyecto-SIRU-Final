package sede_principal.datos;

import excepciones.documentos.*;

/**
 * Firmar digitalmente documentos.
 */
public class ServicioFirmaDigital {
    public String firmarDocumento(String contenido, String idFirmante) {
        if (idFirmante == null || idFirmante.isBlank()) {
            throw new FirmanteNoAutorizadoException("Firmante inválido");
        }
        return contenido + " | Firmado por: " + idFirmante;
    }
}
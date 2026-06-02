package sede_principal.datos;

import excepciones.documentos.*;

/**
 * Responsabilidad: Firmar digitalmente documentos.
 */
public class ServicioFirmaDigital {
    public String firmarDocumento(String contenido, String idFirmante) throws FirmaDigitalException {
        if (idFirmante == null || idFirmante.isBlank()) {
            throw new FirmanteNoAutorizadoException("Firmante inválido");
        }
        // Simulación de firma digital
        return contenido + " | Firmado por: " + idFirmante;
    }
}

package sede_principal.datos;

import excepciones.pago.*;

/**
 * Simular conexión con pasarela de pagos externa porque aun no hay una real.
 */
public class PasarelaExterna {

    public String procesarPago(String idEstudiante, double monto) {
        if (monto <= 0) {
            throw new PagoPendienteException("Monto inválido");
        }
        return "Pago confirmado para estudiante " + idEstudiante;
    }
}
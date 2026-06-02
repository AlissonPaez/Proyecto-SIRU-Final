package sede_principal.datos;

import excepciones.pago.*;

/**
 * Responsabilidad: Simular conexión con pasarela de pagos externa.
 */
public class PasarelaExterna {
    private static final int MAX_REINTENTOS = 3;

    public String procesarPago(String idEstudiante, double monto) throws PagoException {
        if (monto <= 0) {
            throw new PagoPendienteException("Monto inválido");
        }
        // Simulación: pago exitoso en el primer intento
        return "Pago confirmado para estudiante " + idEstudiante;
    }
}

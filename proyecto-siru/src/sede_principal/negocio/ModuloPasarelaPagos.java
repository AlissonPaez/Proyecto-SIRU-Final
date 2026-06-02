package sede_principal.negocio;

import sede_principal.datos.BDAcademica;
import sede_principal.datos.PasarelaExterna;

public class ModuloPasarelaPagos {

    private PasarelaExterna pasarelaExterna;
    private BDAcademica bdAcademica;

    public ModuloPasarelaPagos(PasarelaExterna pasarelaExterna, BDAcademica bdAcademica) {
        this.pasarelaExterna = pasarelaExterna;
        this.bdAcademica = bdAcademica;
    }

    // Inicia el proceso de pago de matrícula del estudiante por el monto indicado.
    // Se conecta con la pasarela externa (PSE/Wompi) para procesar la transacción.
    // Maneja automáticamente los reintentos si el pago falla en el primer intento.
    // Retorna confirmación del pago o el motivo del fallo.
    public String procesarPago(String idEstudiante, double monto) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Consulta el estado actual del pago de matrícula del estudiante en el período indicado.
    // Retorna si el pago está confirmado, pendiente, fallido o no iniciado.
    public String consultarEstado(String idEstudiante, String periodo) {
        throw new UnsupportedOperationException("Por implementar");
    }
}
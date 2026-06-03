package sede_principal.negocio;

import excepciones.pago.*;
import excepciones.validacion.*;
import sede_principal.datos.BDAcademica;
import sede_principal.datos.PasarelaExterna;

import java.util.HashMap;
import java.util.Map;

public class ModuloPasarelaPagos {

    private static final int MAX_REINTENTOS = 3;

    private final PasarelaExterna pasarelaExterna;
    private final BDAcademica bdAcademica;
    private final Map<String, String> estadosPago = new HashMap<>();

    public ModuloPasarelaPagos(PasarelaExterna pasarelaExterna, BDAcademica bdAcademica) {
        this.pasarelaExterna = pasarelaExterna;
        this.bdAcademica = bdAcademica;
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
     * Valida que el monto sea mayor a cero.
     *
     * @param monto Monto a validar.
     * @throws IllegalArgumentException Si el monto es menor o igual a cero.
     */
    private void validarMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
    }

    /**
     * Intenta procesar el pago hasta MAX_REINTENTOS veces.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param monto        Monto a cobrar.
     * @return Confirmación del pago exitoso.
     * @throws PagoFallidoException Si el pago falla en todos los intentos.
     */
    private String intentarPago(String idEstudiante, double monto) {
        for (int intento = 1; intento <= MAX_REINTENTOS; intento++) {
            try {
                return pasarelaExterna.procesarPago(idEstudiante, monto);
            } catch (PagoException e) {
                boolean esUltimoIntento = intento == MAX_REINTENTOS;
                if (esUltimoIntento) {
                    throw new PagoFallidoException(
                        "Pago fallido tras " + MAX_REINTENTOS + " intentos: " + e.getMessage());
                }
            }
        }
        throw new PagoFallidoException("Pago fallido: no se pudo procesar la transacción");
    }

    
    // Métodos públicos

    /**
     * Inicia el proceso de pago de matrícula del estudiante por el monto indicado.
     * Reintenta automáticamente hasta MAX_REINTENTOS veces si el pago falla.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param monto        Monto a cobrar por la matrícula.
     * @return Confirmación del pago o el motivo del fallo.
     * @throws IllegalArgumentException Si algún parámetro es inválido.
     * @throws PagoFallidoException     Si el pago falla en todos los intentos.
     */
    public String procesarPago(String idEstudiante, double monto) {
        validarTexto(idEstudiante, "idEstudiante");
        validarMonto(monto);
        String resultado = intentarPago(idEstudiante, monto);
        estadosPago.put(idEstudiante, "confirmado");
        return resultado;
    }

    /**
     * Consulta el estado actual del pago de matrícula del estudiante en el período indicado.
     *
     * @param idEstudiante Identificador del estudiante.
     * @param periodo      Período académico a consultar.
     * @return Estado del pago: confirmado, pendiente, fallido o no iniciado.
     * @throws IllegalArgumentException Si algún parámetro es nulo o vacío.
     */
    public String consultarEstado(String idEstudiante, String periodo) {
        validarTexto(idEstudiante, "idEstudiante");
        validarTexto(periodo, "periodo");
        return estadosPago.getOrDefault(idEstudiante, "no iniciado");
    }
}
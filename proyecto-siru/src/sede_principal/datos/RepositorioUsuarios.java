package sede_principal.datos;

import java.util.HashMap;
import java.util.Map;

import excepciones.validacion.DatosInvalidosException;

/**
 * Administrar usuarios del sistema.
 */
public class RepositorioUsuarios {
    private final Map<String, String> usuarios = new HashMap<>();

    public void registrarUsuario(String email, String contraseña) {
        usuarios.put(email, contraseña);
    }

    public boolean validarCredenciales(String email, String contraseña) {
        return usuarios.containsKey(email) && usuarios.get(email).equals(contraseña);
    }

        /**
     * Verifica si un email está registrado en el sistema.
     *
     * @param email Email a verificar.
     * @return true si el email existe, false en caso contrario.
     * @throws DatosInvalidosException Si el email es nulo o vacío.
     */
    public boolean existeUsuario(String email) {
        if (email == null || email.isBlank()) {
            throw new DatosInvalidosException("email no puede ser nulo o vacío");
        }
        return usuarios.containsKey(email);
    }
}

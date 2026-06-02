package sede_principal.datos;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsabilidad: Administrar usuarios del sistema.
 */
public class RepositorioUsuarios {
    private final Map<String, String> usuarios = new HashMap<>();

    public void registrarUsuario(String email, String contraseña) {
        usuarios.put(email, contraseña);
    }

    public boolean validarCredenciales(String email, String contraseña) {
        return usuarios.containsKey(email) && usuarios.get(email).equals(contraseña);
    }
}

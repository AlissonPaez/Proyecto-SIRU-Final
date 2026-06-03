package sede_principal.negocio;

import excepciones.autenticacion.*;
import sede_principal.datos.RepositorioUsuarios;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GestorAutenticacion {

    private final RepositorioUsuarios repositorioUsuarios;
    private final Map<String, String> tokensPorEmail = new HashMap<>();

    public GestorAutenticacion(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    // Métodos privados
    
    /**
     * Valida que un parámetro no sea nulo ni vacío.
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
     * Verifica que el email exista en el sistema.
     *
     * @param email Email a verificar.
     * @throws UsuarioNoEncontradoException Si el email no está registrado.
     */
    private void verificarUsuarioExiste(String email) {
        if (!repositorioUsuarios.existeUsuario(email)) {
            throw new UsuarioNoEncontradoException(
                "No existe un usuario registrado con el email: " + email);
        }
    }

    /**
     * Verifica que las credenciales del usuario sean correctas.
     *
     * @param email     Email del usuario.
     * @param contraseña Contraseña a validar.
     * @throws CredencialesInvalidasException Si la contraseña es incorrecta.
     */
    private void verificarCredenciales(String email, String contraseña) {
        if (!repositorioUsuarios.validarCredenciales(email, contraseña)) {
            throw new CredencialesInvalidasException(
                "La contraseña es incorrecta para el usuario: " + email);
        }
    }

    /**
     * Verifica que el token exista y esté activo.
     *
     * @param token Token de sesión a verificar.
     * @throws TokenInvalidoException Si el token no existe o está vencido.
     */
        private void verificarTokenActivo(String token) {
        boolean tokenNoExiste = !tokensPorEmail.containsValue(token);
        if (tokenNoExiste) {
            throw new AutenticacionException(
                "El token no existe o ya fue invalidado: " + token);
        }
    }

    /**
     * Genera un token único y lo asocia al email del usuario.
     *
     * @param email Email del usuario.
     * @return Token de sesión generado.
     */
    private String generarToken(String email) {
        String token = UUID.randomUUID().toString();
        tokensPorEmail.put(email, token);
        return token;
    }

    /**
     * Busca el email asociado a un token activo.
     *
     * @param token Token de sesión.
     * @return Email del usuario dueño del token.
     */
        private String buscarEmailPorToken(String token) {
        return tokensPorEmail.entrySet().stream()
            .filter(entrada -> entrada.getValue().equals(token))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow(() -> new AutenticacionException(
                "No se encontró usuario para el token: " + token));
    }

    // Métodos públicos
    

    /**
     * Verifica las credenciales del usuario y retorna un token de sesión.
     *
     * @param email     Email del usuario registrado.
     * @param contraseña Contraseña del usuario.
     * @return Token de sesión generado para el usuario autenticado.
     * @throws IllegalArgumentException       Si algún parámetro es nulo o vacío.
     * @throws UsuarioNoEncontradoException   Si el email no está registrado.
     * @throws CredencialesInvalidasException Si la contraseña es incorrecta.
     */
    public String iniciarSesion(String email, String contraseña) {
        validarTexto(email, "email");
        validarTexto(contraseña, "contraseña");
        verificarUsuarioExiste(email);
        verificarCredenciales(email, contraseña);
        return generarToken(email);
    }

    /**
     * Valida si el token de sesión es válido y retorna el email del usuario.
     *
     * @param token Token de sesión a verificar.
     * @return Email del usuario asociado al token.
     * @throws IllegalArgumentException Si el token es nulo o vacío.
     * @throws TokenInvalidoException   Si el token no existe o está vencido.
     */
    public String verificarToken(String token) {
        validarTexto(token, "token");
        verificarTokenActivo(token);
        return buscarEmailPorToken(token);
    }

    /**
     * Envía instrucciones de recuperación al email registrado.
     *
     * @param email Email del usuario que solicita recuperación.
     * @return Confirmación del envío del correo de recuperación.
     * @throws IllegalArgumentException     Si el email es nulo o vacío.
     * @throws UsuarioNoEncontradoException Si el email no está registrado.
     */
    public String recuperarContraseña(String email) {
        validarTexto(email, "email");
        verificarUsuarioExiste(email);
        return "Correo de recuperación enviado a: " + email;
    }

    /**
     * Invalida el token de sesión activo para cerrar la sesión del usuario.
     *
     * @param token Token de sesión a invalidar.
     * @return Confirmación de cierre de sesión exitoso.
     * @throws IllegalArgumentException Si el token es nulo o vacío.
     * @throws TokenInvalidoException   Si el token no existe o ya fue invalidado.
     */
    public String cerrarSesion(String token) {
        validarTexto(token, "token");
        verificarTokenActivo(token);
        String email = buscarEmailPorToken(token);
        tokensPorEmail.remove(email);
        return "Sesión cerrada exitosamente para: " + email;
    }
}
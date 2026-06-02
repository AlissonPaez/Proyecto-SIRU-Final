package sede_principal.negocio;

import sede_principal.datos.RepositorioUsuarios;

public class GestorAutenticacion {

    private RepositorioUsuarios repositorioUsuarios;

    public GestorAutenticacion(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    // Verifica las credenciales del usuario y retorna un token de sesión junto con su rol.
    // Retorna error si el usuario no existe o la contraseña es incorrecta.
    public String iniciarSesion(String email, String contraseña) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Valida si el token de sesión es válido y retorna el usuario con sus permisos.
    // Retorna error si el token está vencido o no existe.
    public String verificarToken(String token) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Envía un correo al email registrado con instrucciones para recuperar la contraseña.
    // Retorna error si el email no corresponde a ningún usuario del sistema.
    public String recuperarContraseña(String email) {
        throw new UnsupportedOperationException("Por implementar");
    }

    // Invalida el token de sesión activo del usuario para cerrar su sesión.
    // Retorna confirmación de cierre exitoso.
    public String cerrarSesion() {
        throw new UnsupportedOperationException("Por implementar");
    }
}


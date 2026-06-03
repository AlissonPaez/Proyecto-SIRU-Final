package excepciones.autenticacion;


 // Se lanza cuando se busca un usuario por email y no existe ninguna cuenta registrada con ese correo.
 
public class UsuarioNoEncontradoException extends AutenticacionException {

    public UsuarioNoEncontradoException(String email) {
        super("No existe una cuenta registrada con el correo: " + email);
    }
}

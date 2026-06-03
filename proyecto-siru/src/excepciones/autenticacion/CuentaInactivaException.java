package excepciones.autenticacion;


  // Se lanza cuando un usuario intenta iniciar sesión pero su cuenta está desactivada en el sistema.
 
public class CuentaInactivaException extends AutenticacionException {

    public CuentaInactivaException(String email) {
        super("La cuenta asociada al correo " + email + " está desactivada. Contacte al área administrativa");
    }
}

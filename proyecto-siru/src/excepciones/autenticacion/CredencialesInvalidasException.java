package excepciones.autenticacion;

public class CredencialesInvalidasException extends AutenticacionException {

    public CredencialesInvalidasException() {
        super("La contraseña ingresada es incorrecta");
    }

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
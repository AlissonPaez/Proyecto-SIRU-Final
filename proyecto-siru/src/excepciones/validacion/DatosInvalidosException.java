package excepciones.validacion;


 //Se lanza cuando los parámetros de entrada de un método son nulos, vacíos o tienen un formato incorrecto.
 
public class DatosInvalidosException extends ValidacionException {

    public DatosInvalidosException(String campo) {
        super("El campo '" + campo + "' no puede ser nulo o vacío");
    }
}

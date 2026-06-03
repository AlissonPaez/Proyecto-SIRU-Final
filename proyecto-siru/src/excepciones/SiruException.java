package excepciones;


 // Excepción base del sistema SIRU.
 // Todas las excepciones unchecked del sistema heredan de esta clase.
 // Permite capturar cualquier error de negocio.
 
public class SiruException extends RuntimeException {

    public SiruException(String mensaje) {
        super(mensaje);
    }

    public SiruException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

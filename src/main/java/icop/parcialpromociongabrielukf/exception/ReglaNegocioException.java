package icop.parcialpromociongabrielukf.exception;

/** Operación no permitida por una regla de negocio -> 400 BAD_REQUEST */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}

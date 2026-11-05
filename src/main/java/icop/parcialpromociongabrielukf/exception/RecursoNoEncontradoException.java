package icop.parcialpromociongabrielukf.exception;

/** Recurso inexistente -> 404 NOT_FOUND */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

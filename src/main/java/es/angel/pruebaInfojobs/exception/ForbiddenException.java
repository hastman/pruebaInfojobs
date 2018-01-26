package es.angel.pruebaInfojobs.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Access denied");
    }

    public ForbiddenException(String message) {
        super("Error code 403: Access denied");
    }
}

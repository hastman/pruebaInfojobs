package es.angel.pruebaInfojobs.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException() {
        super("Unauthorized access");
    }

    public UnAuthorizedException(String message) {
        super("Error code 501: Unauthorized access");
    }
}

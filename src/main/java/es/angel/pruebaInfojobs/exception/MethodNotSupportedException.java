package es.angel.pruebaInfojobs.exception;

public class MethodNotSupportedException extends RuntimeException {


    public MethodNotSupportedException() {
        super("Method not supported");
    }

    public MethodNotSupportedException(String message) {
        super("Error 405: Method not supported");
    }
}

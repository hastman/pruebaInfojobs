package es.angel.pruebaInfojobs.exception;

public class InternalError extends RuntimeException {

    public InternalError() {
        super("Internal Error");
    }

    @Override
    public String getMessage() {
        return "Error Code:500, Internal Error";
    }
}

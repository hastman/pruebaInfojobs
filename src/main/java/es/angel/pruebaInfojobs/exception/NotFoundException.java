package es.angel.pruebaInfojobs.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Not Found");
    }

    @Override
    public String getMessage() {
        return "Error Code:404, Not Found";
    }
}

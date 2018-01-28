package es.angel.pruebaInfojobs.exception;

import es.angel.pruebaInfojobs.model.Response;

public abstract class HttpStatusCodeException extends RuntimeException {

    public abstract Response errorResponse();

}

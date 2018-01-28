package es.angel.pruebaInfojobs.exception;

import es.angel.pruebaInfojobs.model.Response;

public class ForbiddenException extends HttpStatusCodeException {

    public ForbiddenException() {

    }

    @Override
    public String getMessage() {
        return "Error code 403: Access denied";
    }

    @Override
    public Response errorResponse() {
        return new Response.Builder().withStatusCode(403).withBody("Access deniend").build();
    }
}

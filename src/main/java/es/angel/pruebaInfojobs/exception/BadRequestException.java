package es.angel.pruebaInfojobs.exception;

import es.angel.pruebaInfojobs.model.Response;

public class BadRequestException extends HttpStatusCodeException {

    @Override
    public String getMessage() {
        return "Bad Request";
    }

    @Override
    public Response errorResponse() {
        return new Response.Builder().withBody("Bad Request").withStatusCode(400).build();
    }
}

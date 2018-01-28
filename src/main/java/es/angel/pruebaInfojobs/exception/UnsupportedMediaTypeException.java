package es.angel.pruebaInfojobs.exception;

import es.angel.pruebaInfojobs.model.Response;

public class UnsupportedMediaTypeException extends HttpStatusCodeException {

    @Override
    public String getMessage() {
        return "Unsupported Media Type";
    }

    @Override
    public Response errorResponse() {
        return new Response.Builder().withStatusCode(415).withBody("Unsupported Media Type").build();
    }
}

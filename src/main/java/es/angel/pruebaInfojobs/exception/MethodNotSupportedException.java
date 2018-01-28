package es.angel.pruebaInfojobs.exception;

import es.angel.pruebaInfojobs.model.Response;

public class MethodNotSupportedException extends HttpStatusCodeException {

    @Override
    public String getMessage() {
        return "Error 405: Method not supported";
    }

    @Override
    public Response errorResponse() {
        return new Response.Builder().withStatusCode(405).withBody("Method not supported").build();
    }
}

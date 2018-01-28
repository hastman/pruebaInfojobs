package es.angel.pruebaInfojobs.exception;

import es.angel.pruebaInfojobs.model.Response;

public class InternalError extends HttpStatusCodeException {

    @Override
    public Response errorResponse() {
        return new Response.Builder().withStatusCode(500).withBody("Internal Error").build();
    }
}

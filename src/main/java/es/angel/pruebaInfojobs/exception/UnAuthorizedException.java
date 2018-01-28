package es.angel.pruebaInfojobs.exception;

import es.angel.pruebaInfojobs.model.Response;

public class UnAuthorizedException extends HttpStatusCodeException {

    @Override
    public String getMessage() {
        return "Error code 401: Unauthorized access";
    }

    @Override
    public Response errorResponse() {
        return new Response.Builder().withStatusCode(401).withBody("Unauthorized access").build();
    }
}

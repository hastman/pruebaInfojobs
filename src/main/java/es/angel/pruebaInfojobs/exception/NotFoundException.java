package es.angel.pruebaInfojobs.exception;

import es.angel.pruebaInfojobs.model.Response;

public class NotFoundException extends HttpStatusCodeException {


    @Override
    public String getMessage() {
        return "Error Code:404, Not Found";
    }

    @Override
    public Response errorResponse() {
        return new Response.Builder().withStatusCode(404).withBody("Not Found").build();
    }
}

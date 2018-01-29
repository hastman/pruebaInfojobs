package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.exception.MethodNotSupportedException;
import es.angel.pruebaInfojobs.model.Response;
import es.angel.pruebaInfojobs.viewresolver.ViewResolver;

import java.util.Map;

public abstract class HttpController {

    ViewResolver viewResolver;

    public Response doPost(final Map<String, String> parameters) {
        throw new MethodNotSupportedException();
    }

    public Response doGet(final Map<String, String> parameters) {
        throw new MethodNotSupportedException();
    }

    public Response doDelete(final Map<String, String> parameters) {
        throw new MethodNotSupportedException();
    }

    public Response doPut(final Map<String, String> parameters) {
        throw new MethodNotSupportedException();
    }

    Response performResponseWithTemplateMessage(String template, String[] messages) {
        final String body = viewResolver.resolveView(template, messages);
        return new Response.Builder().withBody(body).withContentType("text/html").withStatusCode(200).build();
    }
}

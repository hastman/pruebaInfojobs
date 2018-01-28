package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.exception.MethodNotSupportedException;
import es.angel.pruebaInfojobs.htlmview.HtmlViewResolver;
import es.angel.pruebaInfojobs.htlmview.ViewResolver;
import es.angel.pruebaInfojobs.model.Response;

import java.util.Map;


public class LoginController implements HttpController {


    private ViewResolver viewResolver = new HtmlViewResolver();

    @Override
    public Response doPost(final Map<String, String> parameters) {
        final String body = viewResolver.resolveView("templates/main.html", null);
        return new Response.Builder().withBody(body).withContentType("text/html").withStatusCode(200).build();
    }

    @Override
    public Response doGet(final Map<String, String> parameters) {
        final String body = viewResolver.resolveView("templates/login.html", null);
        return new Response.Builder().withBody(body).withContentType("text/html").withStatusCode(200).build();
    }


    @Override
    public Response doDelelete(final Map<String, String> parameters) {
        throw new MethodNotSupportedException();
    }

    @Override
    public Response doPut(final Map<String, String> parameters) {
        throw new MethodNotSupportedException();
    }
}

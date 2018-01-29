package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.model.Response;
import es.angel.pruebaInfojobs.viewresolver.HtmlViewResolver;
import es.angel.pruebaInfojobs.viewresolver.RestViewResolver;

import java.util.Map;

public class ErrorController extends HttpController {

    @Override
    public Response doGet(final Map<String, String> parameters) {
        final String acceptType = parameters.getOrDefault("Accept", "text/html");
        final String error = parameters.getOrDefault("ERROR", "Error code 500: Internal error");
        final Integer statusCode = Integer.valueOf(parameters.getOrDefault("STATUS_CODE", "500"));
        String[] errorMessages = new String[]{error};
        String template = "templates/error.html";
        viewResolver = new HtmlViewResolver();
        if (!acceptType.equals("text/html")) {
            template = acceptType;
            viewResolver = new RestViewResolver();
            errorMessages = new String[]{"error", error};
        }
        final String body = viewResolver.resolveView(template, errorMessages);
        return new Response.Builder().withBody(body)
                .withStatusCode(statusCode)
                .withContentType(acceptType).build();
    }


}

package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.exception.MethodNotSupportedException;
import es.angel.pruebaInfojobs.htlmview.HtmlViewResolver;
import es.angel.pruebaInfojobs.htlmview.RestViewResolver;
import es.angel.pruebaInfojobs.htlmview.ViewResolver;
import es.angel.pruebaInfojobs.model.Response;

import java.util.Map;

public class ErrorController extends HttpController {

    @Override
    public Response doGet(final Map<String, String> parameters) {
        final String contentType = parameters.getOrDefault("CONTENT_TYPE", "text/html");
        final String error = parameters.getOrDefault("ERROR", "Error code 500: Internal error");
        String[] errorMessages = new String[]{error};
        String template = "html/error.html";
        viewResolver = new HtmlViewResolver();
        if (!contentType.equals("text/html")) {
            template = contentType;
            viewResolver = new RestViewResolver();
            errorMessages = new String[]{"error", error};
        }
        final String body = viewResolver.resolveView(template, errorMessages);
        return new Response.Builder().withBody(body)
                .withStatusCode(Integer.valueOf(parameters.getOrDefault("STATUS_CODE", "500")))
                .withContentType(contentType).build();
    }


}

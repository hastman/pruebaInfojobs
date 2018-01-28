package es.angel.pruebaInfojobs.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import es.angel.pruebaInfojobs.controller.ErrorController;
import es.angel.pruebaInfojobs.exception.HttpStatusCodeException;
import es.angel.pruebaInfojobs.model.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class RoutesHandle implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        final String path = httpExchange.getRequestURI().getPath();
        final String cleanedPath = path.replaceAll("/", "");
        final String method = httpExchange.getRequestMethod().toUpperCase();
        final String contentType = httpExchange.getResponseHeaders().getFirst("Content-Type");

        final RoutesDefinition routesDefinition = RoutesDefinition.obtainDefinition(cleanedPath);
        final Map<String, String> parameters = new HashMap<>();

        try {
            final HttpMethodStrategy methodStrategy = new HttpMethodStrategy(routesDefinition.controller());
            final Response response = methodStrategy.responseForMethod(method);
            sendResponse(httpExchange, response);
        } catch (HttpStatusCodeException e) {
            System.err.println("Error when perform action " + e.getMessage());
            final Response errorResponse = e.errorResponse();
            sendResponse(httpExchange, new Response.Builder()
                    .withBody(errorResponse.getBodyContent())
                    .withContentType(contentType)
                    .withStatusCode(errorResponse.getStatusCode())
                    .build());
        } catch (IllegalAccessException | InstantiationException e) {
            System.err.println("Error at instantiate controller " + e);
            parameters.put("ERROR", e.getMessage());
            parameters.put("CONTENT_TYPE", contentType);
            sendResponse(httpExchange, new ErrorController().doGet(parameters));
        } finally {
            httpExchange.close();
        }
    }

    private void sendResponse(HttpExchange httpExchange, Response response) throws IOException {
        final OutputStream responseStream = httpExchange.getResponseBody();
        final byte[] bodyBytes = response.getBodyContent().getBytes();
        httpExchange.sendResponseHeaders(response.getStatusCode(), bodyBytes.length);
        httpExchange.getResponseHeaders().set("Content-Type", response.getContentType());
        responseStream.write(bodyBytes);
        responseStream.flush();
    }
}
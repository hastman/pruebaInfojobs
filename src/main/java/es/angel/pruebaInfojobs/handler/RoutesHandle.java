package es.angel.pruebaInfojobs.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import es.angel.pruebaInfojobs.controller.ErrorController;
import es.angel.pruebaInfojobs.controller.HttpController;
import es.angel.pruebaInfojobs.model.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class RoutesHandle implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        final String path = httpExchange.getRequestURI().getPath();
        final String cleanedPath = path.replaceAll("/", "");
        final String method = httpExchange.getRequestMethod();

        final RoutesDefinition routesDefinition = RoutesDefinition.obtainDefinition(cleanedPath);
        final Map<String, String> parameters = new HashMap<>();
        try {
            final HttpController controller = routesDefinition.controller();
            final Response response = controller.doGet(parameters);
            sendResponse(httpExchange, response);
        } catch (RuntimeException | IllegalAccessException | InstantiationException e) {
            System.err.println("Error at instanciate controller " + e);
            parameters.put("ERROR", e.getMessage());
            parameters.put("CONTENT_TYPE", httpExchange.getResponseHeaders().getFirst("content-type"));
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

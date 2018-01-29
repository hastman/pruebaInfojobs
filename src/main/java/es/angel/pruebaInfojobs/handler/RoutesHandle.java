package es.angel.pruebaInfojobs.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import es.angel.pruebaInfojobs.controller.ErrorController;
import es.angel.pruebaInfojobs.exception.HttpStatusCodeException;
import es.angel.pruebaInfojobs.helper.ParamsHelper;
import es.angel.pruebaInfojobs.model.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RoutesHandle implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            checkSecException(httpExchange);
            final String path = Stream.of(httpExchange.getRequestURI().getPath().split("/"))
                    .filter(p -> p.length() > 0)
                    .findFirst()
                    .orElse("");
            final String method = httpExchange.getRequestMethod().toUpperCase();
            final Map<String, String> parameters = ParamsHelper.extractParams(httpExchange);
            final RoutesDefinition routesDefinition = RoutesDefinition.obtainDefinition(path);
            final HttpMethodStrategy methodStrategy = new HttpMethodStrategy(routesDefinition.controller(), parameters);
            final Response response = methodStrategy.responseForMethod(method);
            sendResponse(httpExchange, response);
        } catch (HttpStatusCodeException e) {
            System.err.println("Error when perform action " + e.getMessage());
            final Response errorResponse = e.errorResponse();
            sendResponse(httpExchange, new Response.Builder()
                    .withBody(errorResponse.getBodyContent())
                    .withContentType(httpExchange.getResponseHeaders().getFirst("Content-Type"))
                    .withStatusCode(errorResponse.getStatusCode())
                    .build());
        } catch (IllegalAccessException | InstantiationException e) {
            System.err.println("Error at instantiate controller " + e);
            final Map<String, String> parameters = new HashMap<>();
            parameters.put("ERROR", e.getMessage());
            sendResponse(httpExchange, new ErrorController().doGet(parameters));
        } finally {
            httpExchange.close();
        }
    }

    private void checkSecException(HttpExchange httpExchange) {
        final Object secException = httpExchange.getAttribute("SEC_EXCEPTION");
        if (secException != null) {
            throw (HttpStatusCodeException) secException;
        }
    }

    private void sendResponse(HttpExchange httpExchange, Response response) throws IOException {
        final OutputStream responseStream = httpExchange.getResponseBody();
        final byte[] bodyBytes = response.getBodyContent().getBytes();
        httpExchange.sendResponseHeaders(response.getStatusCode(), 0);
        httpExchange.getResponseHeaders().set("Content-Type", response.getContentType());
        responseStream.write(bodyBytes);
        responseStream.flush();
    }
}
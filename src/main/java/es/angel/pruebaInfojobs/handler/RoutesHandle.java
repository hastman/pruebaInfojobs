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
            final Response response = HttpMethodStrategy.responseForMetod(method, routesDefinition.controller(), parameters);
            sendResponse(httpExchange, response);
        } catch (HttpStatusCodeException e) {
            System.err.println("Error when perform action " + e.getMessage());
            final Response errorResponse = e.errorResponse();
            final Map<String, String> parameters = new HashMap<>();
            parameters.put("ERROR", e.getMessage());
            parameters.put("STATUS_CODE", String.valueOf(errorResponse.getStatusCode()));
            parameters.put("Accept", httpExchange.getRequestHeaders().getFirst("Accept").split(",")[0]);
            httpExchange.setAttribute("SEC_EXCEPTION", null);
            sendResponse(httpExchange, new ErrorController().doGet(parameters));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.err.println("Error at instantiate controller " + e);
            final Map<String, String> parameters = new HashMap<>();
            parameters.put("ERROR", e.getMessage());
            parameters.put("Accept", httpExchange.getRequestHeaders().getFirst("Accept").split(",")[0]);
            httpExchange.setAttribute("SEC_EXCEPTION", null);
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
        httpExchange.getResponseHeaders().set("Accept", response.getContentType());
        httpExchange.getResponseHeaders().set("Content-type", response.getContentType());
        responseStream.write(bodyBytes);
        responseStream.flush();
    }
}
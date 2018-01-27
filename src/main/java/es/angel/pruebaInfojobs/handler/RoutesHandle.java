package es.angel.pruebaInfojobs.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RoutesHandle implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        final String path = httpExchange.getRequestURI().getPath();
        final String cleanedPath = path.replaceAll("/", "");


        httpExchange.getResponseHeaders().add("set-cookie","id=123;");

        final RoutesDefinition routesDefinition = RoutesDefinition.obtainDefinition(cleanedPath);




        httpExchange.getRequestMethod();
        httpExchange.close();


    }
}

package es.angel.pruebaInfojobs;


import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import es.angel.pruebaInfojobs.filter.SecurityFilter;
import es.angel.pruebaInfojobs.handler.RoutesHandle;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        final HttpContext context = server.createContext("/", new RoutesHandle());
        context.getFilters().add(new SecurityFilter());
        server.setExecutor(null);
        server.start();

    }
}

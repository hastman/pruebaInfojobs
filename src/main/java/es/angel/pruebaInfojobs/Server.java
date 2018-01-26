package es.angel.pruebaInfojobs;


import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import es.angel.pruebaInfojobs.handler.RoutesHandle;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        final HttpContext context = server.createContext("/", new RoutesHandle());

        server.setExecutor(null); // creates a default executor
        server.start();

    }
}

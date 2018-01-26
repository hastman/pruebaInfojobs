package es.angel.pruebaInfojobs.filter;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import es.angel.pruebaInfojobs.handler.RoutesDefinition;

import java.io.IOException;
import java.net.HttpCookie;

public class SecurityFilter extends Filter {

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

        final String clearPath = httpExchange.getHttpContext().getPath().replaceAll("/", "");
        final RoutesDefinition routesDefinition = RoutesDefinition.obtainDefinition(clearPath);
        if (routesDefinition.isSecure()){

            //scar cookie y comprobas si la sesion es valida
        }


    }

    @Override
    public String description() {
        return "Security filter, check session and rights when user try access to secured page";
    }
}

package es.angel.pruebaInfojobs.filter;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import es.angel.pruebaInfojobs.handler.RoutesDefinition;

import java.io.IOException;
import java.util.stream.Stream;

public class SecurityFilter extends Filter {

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

        final String path = Stream.of(httpExchange.getRequestURI().getPath().split("/"))
                .filter(p -> p.length() > 0)
                .findFirst()
                .orElse("");
        final RoutesDefinition routesDefinition = RoutesDefinition.obtainDefinition(path);
        if (routesDefinition.isSecure()) {
            Authenticator.Result result = routesDefinition.isRest() ? new RestAuthenticator("users").authenticate(httpExchange)
                    : new WebAuthenticator().authenticate(httpExchange);
        }
        chain.doFilter(httpExchange);
    }


    @Override
    public String description() {
        return "Security filter, delegate authentication and authorization in concretes Authenticator depends if is rest or web";
    }
}

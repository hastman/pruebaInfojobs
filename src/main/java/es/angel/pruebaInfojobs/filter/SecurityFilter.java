package es.angel.pruebaInfojobs.filter;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import es.angel.pruebaInfojobs.exception.ForbiddenException;
import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import es.angel.pruebaInfojobs.handler.RoutesDefinition;
import es.angel.pruebaInfojobs.helper.ParamsHelper;
import es.angel.pruebaInfojobs.model.Access;
import es.angel.pruebaInfojobs.model.Session;
import es.angel.pruebaInfojobs.model.Users;

import java.io.IOException;
import java.util.Optional;
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
            final String token = extractSessionToken(ParamsHelper.extractParams(httpExchange).get("Cookie"), httpExchange, chain);
            final Optional<Session> session = new Session(token).hasSession();
            checkSession(httpExchange, chain, session);
            validSession(httpExchange, chain, session.get());
            canAccess(httpExchange, chain, path, session.get());
            chain.doFilter(httpExchange);
        }
        chain.doFilter(httpExchange);
    }

    private String extractSessionToken(String cookie, HttpExchange httpExchange, Chain chain) throws IOException {
        if (!Optional.ofNullable(cookie).isPresent()) {
            httpExchange.setAttribute("SEC_EXCEPTION", new UnAuthorizedException());
            chain.doFilter(httpExchange);
        }
        return cookie.split("=")[1];
    }

    private void canAccess(HttpExchange httpExchange, Chain chain, String path, Session session) throws IOException {
        final Users users = new Users();
        users.setUserName(session.getUserName());
        final Users completeUserData = users.findByUserName();
        boolean canAccess = completeUserData.getRoles().stream().map(role -> {
            Access access = new Access();
            access.setRole(role);
            return access.findByRole();
        }).anyMatch(access -> access.canAccess(httpExchange.getRequestMethod(), path));
        if (!canAccess) {
            httpExchange.setAttribute("SEC_EXCEPTION", new ForbiddenException());
            chain.doFilter(httpExchange);
        }
    }

    private void validSession(HttpExchange httpExchange, Chain chain, Session session) throws IOException {
        if (!session.isValidSession()) {
            httpExchange.setAttribute("SEC_EXCEPTION", new UnAuthorizedException());
            chain.doFilter(httpExchange);
        }
    }

    private void checkSession(HttpExchange httpExchange, Chain chain, Optional<Session> session) throws IOException {
        if (!session.isPresent()) {
            httpExchange.setAttribute("SEC_EXCEPTION", new UnAuthorizedException());
            chain.doFilter(httpExchange);
        }
    }

    @Override
    public String description() {
        return "Security filter, check session and rights when user try access to secured page";
    }
}

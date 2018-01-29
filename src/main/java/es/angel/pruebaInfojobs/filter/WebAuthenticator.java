package es.angel.pruebaInfojobs.filter;


import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import es.angel.pruebaInfojobs.exception.ForbiddenException;
import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import es.angel.pruebaInfojobs.helper.ParamsHelper;
import es.angel.pruebaInfojobs.model.Access;
import es.angel.pruebaInfojobs.model.Session;
import es.angel.pruebaInfojobs.model.Users;

import java.util.Optional;
import java.util.stream.Stream;

public class WebAuthenticator extends Authenticator {

    @Override
    public Result authenticate(HttpExchange httpExchange) {
        final String path = Stream.of(httpExchange.getRequestURI().getPath().split("/"))
                .filter(p -> p.length() > 0)
                .findFirst()
                .orElse("");
        final String token = extractSessionToken(ParamsHelper.extractParams(httpExchange).get("Cookie"), httpExchange);
        final Session session = new Session(token).hasSession().orElseGet(null);
        checkSession(httpExchange, session);
        validSession(httpExchange, session);
        canAccess(httpExchange, path, session);
        if (httpExchange.getAttribute("SEC_EXCEPTION") != null) {
            return new Failure(401);
        }
        httpExchange.setAttribute("USER_IN_SESSION", session.getUserName());
        return new Success(new HttpPrincipal(session.getUserName(), "users"));
    }

    private String extractSessionToken(String cookie, HttpExchange httpExchange) {
        if (!Optional.ofNullable(cookie).isPresent()) {
            httpExchange.setAttribute("SEC_EXCEPTION", new UnAuthorizedException());
        }
        return cookie.split("=")[1];
    }

    private void canAccess(HttpExchange httpExchange, String path, Session session) {
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
        }
    }

    private void validSession(HttpExchange httpExchange, Session session) {
        if (!session.isValidSession()) {
            httpExchange.setAttribute("SEC_EXCEPTION", new UnAuthorizedException());
        }
    }

    private void checkSession(HttpExchange httpExchange, Session session) {
        if (session == null) {
            httpExchange.setAttribute("SEC_EXCEPTION", new UnAuthorizedException());
        }
    }
}

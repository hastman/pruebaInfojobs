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

    private static final String SEC_EXCEPTION = "SEC_EXCEPTION";

    @Override
    public Result authenticate(HttpExchange httpExchange) {
        final String path = Stream.of(httpExchange.getRequestURI().getPath().split("/"))
                .filter(p -> p.length() > 0)
                .findFirst()
                .orElse("");
        final String token = extractSessionToken(ParamsHelper.extractParams(httpExchange).get("Cookie"), httpExchange);
        if (token == null) {
            return new Failure(401);
        }
        final Session session = new Session(token).hasSession().orElse(null);
        if (session == null) {
            httpExchange.setAttribute(SEC_EXCEPTION, new UnAuthorizedException());
            return new Failure(401);
        }
        if (!isValidSession(httpExchange, session)) {
            return new Failure(401);
        }
        if (!canAccess(httpExchange, path, session)) {
            return new Failure(403);
        }
        httpExchange.setAttribute("USER_IN_SESSION", session.getUserName());
        return new Success(new HttpPrincipal(session.getUserName(), "users"));

    }

    private String extractSessionToken(String cookie, HttpExchange httpExchange) {
        if (!Optional.ofNullable(cookie).isPresent()) {
            httpExchange.setAttribute(SEC_EXCEPTION, new UnAuthorizedException());
            return null;
        }
        return cookie.split("=")[1];
    }

    private boolean canAccess(HttpExchange httpExchange, String path, Session session) {
        final Users users = new Users();
        users.setUserName(session.getUserName());
        final Users completeUserData = users.findByUserName();
        boolean canAccess = completeUserData.getRoles().stream().map(role -> {
            Access access = new Access();
            access.setRole(role);
            return access.findByRole();
        }).anyMatch(access -> access.canAccess(httpExchange.getRequestMethod(), path));
        if (!canAccess) {
            httpExchange.setAttribute(SEC_EXCEPTION, new ForbiddenException());
            return false;
        }
        return true;
    }

    private boolean isValidSession(HttpExchange httpExchange, Session session) {
        if (session == null || !session.isValidSession()) {
            httpExchange.setAttribute(SEC_EXCEPTION, new UnAuthorizedException());
            return false;
        }
        return true;
    }
}

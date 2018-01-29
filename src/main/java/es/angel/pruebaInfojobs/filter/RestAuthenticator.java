package es.angel.pruebaInfojobs.filter;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import es.angel.pruebaInfojobs.exception.ForbiddenException;
import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import es.angel.pruebaInfojobs.helper.CryptoHelper;
import es.angel.pruebaInfojobs.model.Access;
import es.angel.pruebaInfojobs.model.Users;

import java.util.stream.Stream;

public class RestAuthenticator extends BasicAuthenticator {

    private Users userInDB;

    RestAuthenticator(String s) {
        super(s);
    }

    @Override
    public Result authenticate(HttpExchange httpExchange) {
        Result authenticate = super.authenticate(httpExchange);
        if (!(authenticate instanceof Authenticator.Success)) {
            httpExchange.setAttribute("SEC_EXCEPTION", new UnAuthorizedException());
            return authenticate;
        }

        return canAccess(httpExchange);
    }

    @Override
    public boolean checkCredentials(String userName, String passwd) {
        userInDB = new Users.Builder().withUserName(userName).build().findByUserName();
        return userInDB.getPassword().equals(CryptoHelper.hashData(passwd));

    }

    private Result canAccess(HttpExchange httpExchange) {
        final String path = Stream.of(httpExchange.getRequestURI().getPath().split("/"))
                .filter(p -> p.length() > 0)
                .findFirst()
                .orElse("");
        boolean canAccess = userInDB.getRoles().stream().map(role -> {
            Access access = new Access();
            access.setRole(role);
            return access.findByRole();
        }).anyMatch(access -> access.canAccess(httpExchange.getRequestMethod(), path));
        if (!canAccess) {
            httpExchange.setAttribute("SEC_EXCEPTION", new ForbiddenException());
            return new Failure(403);
        }
        return new Success(new HttpPrincipal(userInDB.getUserName(), "users"));
    }
}

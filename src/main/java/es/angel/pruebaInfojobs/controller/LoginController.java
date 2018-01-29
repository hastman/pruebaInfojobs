package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import es.angel.pruebaInfojobs.model.Response;
import es.angel.pruebaInfojobs.model.Session;
import es.angel.pruebaInfojobs.model.Users;
import es.angel.pruebaInfojobs.viewresolver.HtmlViewResolver;

import java.util.Map;
import java.util.UUID;


public class LoginController extends HttpController {

    public LoginController() {
        viewResolver = new HtmlViewResolver();
    }

    @Override
    public Response doPost(final Map<String, String> parameters) {
        final String userName = parameters.get("userName");
        final String password = parameters.get("password");
        if (!new Users.Builder().withUserName(userName).withPassword(password).build().isUser()) {
            throw new UnAuthorizedException();
        }
        final String uuidSession = UUID.randomUUID().toString();
        new Session(uuidSession, userName).saveSession();
        return performResponseWithTemplateMessage("templates/main.html", new String[]{uuidSession});
    }

    @Override
    public Response doGet(final Map<String, String> parameters) {
        return performResponseWithTemplateMessage("templates/login.html", null);
    }

}

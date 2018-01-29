package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import es.angel.pruebaInfojobs.htlmview.HtmlViewResolver;
import es.angel.pruebaInfojobs.model.Response;
import es.angel.pruebaInfojobs.model.Session;
import es.angel.pruebaInfojobs.model.Users;

import java.util.Map;


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
        new Session(userName).saveSession();
        return performResponseWithTemplateMessage("templates/main.html", null);
    }

    @Override
    public Response doGet(final Map<String, String> parameters) {
        return performResponseWithTemplateMessage("templates/login.html", null);
    }

}

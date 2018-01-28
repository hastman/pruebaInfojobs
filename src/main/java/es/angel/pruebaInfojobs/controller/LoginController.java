package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.htlmview.HtmlViewResolver;
import es.angel.pruebaInfojobs.model.Response;

import java.util.Map;


public class LoginController extends HttpController {

    public LoginController() {
        viewResolver = new HtmlViewResolver();
    }

    @Override
    public Response doPost(final Map<String, String> parameters) {
        return performResponseWithTemplateMessage("templates/main.html", null);
    }

    @Override
    public Response doGet(final Map<String, String> parameters) {
        return performResponseWithTemplateMessage("templates/login.html", null);
    }

}

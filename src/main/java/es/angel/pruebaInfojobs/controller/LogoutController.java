package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.model.Response;
import es.angel.pruebaInfojobs.viewresolver.HtmlViewResolver;

import java.util.Map;

public class LogoutController extends HttpController {

    public LogoutController() {
        this.viewResolver = new HtmlViewResolver();
    }

    @Override
    public Response doGet(Map<String, String> parameters) {
        return performResponseWithTemplateMessage("templates/login.html", null);
    }
}

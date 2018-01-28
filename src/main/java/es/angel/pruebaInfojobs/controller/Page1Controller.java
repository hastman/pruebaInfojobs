package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.htlmview.HtmlViewResolver;
import es.angel.pruebaInfojobs.model.Response;

import java.util.Map;

public class Page1Controller extends HttpController {

    public Page1Controller() {
        this.viewResolver = new HtmlViewResolver();
    }

    @Override
    public Response doGet(final Map<String, String> parameters) {
        return performResponseWithTemplateMessage("templates/Page1.html", null);

    }


}

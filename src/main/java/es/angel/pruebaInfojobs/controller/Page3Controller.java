package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.htlmview.HtmlViewResolver;
import es.angel.pruebaInfojobs.model.Response;

import java.util.Map;

public class Page3Controller extends HttpController {

    public Page3Controller() {
        this.viewResolver = new HtmlViewResolver();
    }

    @Override
    public Response doGet(final Map<String, String> parameters) {
        return performResponseWithTemplateMessage("templates/Page3.html", null);
    }


}

package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.htlmview.RestViewResolver;
import es.angel.pruebaInfojobs.model.Response;

import java.util.Map;

public class UsersController extends HttpController {

    public UsersController() {
        this.viewResolver = new RestViewResolver();
    }

    @Override
    public Response doPost(final Map<String, String> parameters) {
        return null;
    }

    @Override
    public Response doGet(final Map<String, String> parameters) {
        return null;
    }

    @Override
    public Response doDelelete(final Map<String, String> parameters) {
        return null;
    }

    @Override
    public Response doPut(final Map<String, String> parameters) {
        return null;
    }
}

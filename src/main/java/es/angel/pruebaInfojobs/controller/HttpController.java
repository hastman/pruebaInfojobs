package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.model.Response;

import java.util.Map;

public interface HttpController {

    Response doPost(final Map<String, String> parameters);

    Response doGet(final Map<String, String> parameters);

    Response doDelelete(final Map<String, String> parameters);

    Response doPut(final Map<String, String> parameters);
}

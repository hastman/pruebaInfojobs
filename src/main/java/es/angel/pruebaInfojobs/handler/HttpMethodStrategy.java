package es.angel.pruebaInfojobs.handler;

import es.angel.pruebaInfojobs.controller.HttpController;
import es.angel.pruebaInfojobs.exception.HttpStatusCodeException;
import es.angel.pruebaInfojobs.exception.MethodNotSupportedException;
import es.angel.pruebaInfojobs.model.Response;

import java.util.HashMap;
import java.util.Map;

public class HttpMethodStrategy {

    private Map<String, Response> methodAndResponseMap = new HashMap<>();

    HttpMethodStrategy(HttpController httpController, Map<String, String> parms) {
        this.methodAndResponseMap.put("GET", this.get(httpController, parms));
        this.methodAndResponseMap.put("POST", this.post(httpController, parms));
        this.methodAndResponseMap.put("PUT", this.put(httpController, parms));
        this.methodAndResponseMap.put("DELETE", this.delete(httpController, parms));
    }

    public Response responseForMethod(String method) {
        if (!methodAndResponseMap.containsKey(method)) {
            throw new MethodNotSupportedException();
        }
        return methodAndResponseMap.get(method);
    }

    private Response post(HttpController httpController, Map<String, String> parms) {
        try {
            return httpController.doPost(parms);
        } catch (HttpStatusCodeException ex) {
            return ex.errorResponse();
        }
    }

    private Response put(HttpController httpController, Map<String, String> parms) {
        try {
            return httpController.doPut(parms);
        } catch (HttpStatusCodeException ex) {
            return ex.errorResponse();
        }
    }

    private Response delete(HttpController httpController, Map<String, String> parms) {
        try {
            return httpController.doDelelete(parms);
        } catch (HttpStatusCodeException ex) {
            return ex.errorResponse();
        }
    }

    private Response get(HttpController httpController, Map<String, String> parms) {
        try {
            return httpController.doGet(parms);
        } catch (HttpStatusCodeException ex) {
            return ex.errorResponse();
        }
    }
}

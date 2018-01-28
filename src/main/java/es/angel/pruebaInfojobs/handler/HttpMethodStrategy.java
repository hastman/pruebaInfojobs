package es.angel.pruebaInfojobs.handler;

import es.angel.pruebaInfojobs.controller.HttpController;
import es.angel.pruebaInfojobs.exception.HttpStatusCodeException;
import es.angel.pruebaInfojobs.exception.MethodNotSupportedException;
import es.angel.pruebaInfojobs.model.Response;

import java.util.HashMap;
import java.util.Map;

public class HttpMethodStrategy {

    private Map<String, Response> methodAndResponseMap = new HashMap<>();

    public HttpMethodStrategy(HttpController httpController) {
        this.methodAndResponseMap.put("GET", this.get(httpController));
        this.methodAndResponseMap.put("POST", this.post(httpController));
        this.methodAndResponseMap.put("PUT", this.put(httpController));
        this.methodAndResponseMap.put("DELETE", this.delete(httpController));
    }

    public Response responseForMethod(String method) {
        if (!methodAndResponseMap.containsKey(method)) {
            throw new MethodNotSupportedException();
        }
        return methodAndResponseMap.get(method);
    }

    private Response post(HttpController httpController) {
        try {
            return httpController.doPost(null);
        } catch (HttpStatusCodeException ex) {
            return ex.errorResponse();
        }
    }

    private Response put(HttpController httpController) {
        try {
            return httpController.doPut(null);
        } catch (HttpStatusCodeException ex) {
            return ex.errorResponse();
        }
    }

    private Response delete(HttpController httpController) {
        try {
            return httpController.doDelelete(null);
        } catch (HttpStatusCodeException ex) {
            return ex.errorResponse();
        }
    }

    private Response get(HttpController httpController) {
        try {
            return httpController.doGet(null);
        } catch (HttpStatusCodeException ex) {
            return ex.errorResponse();
        }
    }
}

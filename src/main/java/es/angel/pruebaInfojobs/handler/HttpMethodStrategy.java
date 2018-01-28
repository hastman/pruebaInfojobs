package es.angel.pruebaInfojobs.handler;

import es.angel.pruebaInfojobs.controller.HttpController;
import es.angel.pruebaInfojobs.exception.MethodNotSupportedException;
import es.angel.pruebaInfojobs.model.Response;

import java.util.HashMap;
import java.util.Map;

public class HttpMethodStrategy {

    private Map<String, Response> methodAndResponseMap = new HashMap<>();

    public HttpMethodStrategy(HttpController httpController) {
        this.methodAndResponseMap.put("GET", httpController.doGet(null));
        this.methodAndResponseMap.put("POST", httpController.doPost(null));
        this.methodAndResponseMap.put("PUT", httpController.doPut(null));
        this.methodAndResponseMap.put("DELETE", httpController.doDelelete(null));
    }

    public Response responseForMethod(String method) {
        if (!methodAndResponseMap.containsKey(method)) {
            throw new MethodNotSupportedException();
        }
        return methodAndResponseMap.get(method);
    }
}

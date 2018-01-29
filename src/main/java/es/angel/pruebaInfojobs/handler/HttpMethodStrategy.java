package es.angel.pruebaInfojobs.handler;

import es.angel.pruebaInfojobs.controller.HttpController;
import es.angel.pruebaInfojobs.exception.HttpStatusCodeException;
import es.angel.pruebaInfojobs.exception.MethodNotSupportedException;
import es.angel.pruebaInfojobs.model.Response;

import java.util.Map;
import java.util.stream.Stream;

public enum HttpMethodStrategy {


    GET {
        @Override
        public Response response(HttpController httpController, Map<String, String> params) {
            return httpController.doGet(params);
        }
    },
    POST {
        @Override
        public Response response(HttpController httpController, Map<String, String> params) {
            return httpController.doPost(params);
        }
    }, PUT {
        @Override
        public Response response(HttpController httpController, Map<String, String> params) {
            return httpController.doPut(params);
        }
    }, DELETE {
        @Override
        public Response response(HttpController httpController, Map<String, String> params) {
            return httpController.doDelete(params);
        }
    };

    public static Response responseForMetod(String method, HttpController httpController, Map<String, String> params) {
        return Stream.of(HttpMethodStrategy.values())
                .filter(httpMethod -> httpMethod.name().equals(method))
                .findFirst().orElseThrow(MethodNotSupportedException::new)
                .response(httpController, params);
    }

    public abstract Response response(HttpController httpController, Map<String, String> params);


}

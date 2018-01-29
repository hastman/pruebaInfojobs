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
            try {
                return httpController.doGet(params);
            } catch (HttpStatusCodeException ex) {
                return ex.errorResponse();
            }
        }
    },
    POST {
        @Override
        public Response response(HttpController httpController, Map<String, String> params) {
            try {
                return httpController.doPost(params);
            } catch (HttpStatusCodeException ex) {
                return ex.errorResponse();
            }
        }
    }, PUT {
        @Override
        public Response response(HttpController httpController, Map<String, String> params) {
            try {
                return httpController.doPut(params);
            } catch (HttpStatusCodeException ex) {
                return ex.errorResponse();
            }
        }
    }, DELETE {
        @Override
        public Response response(HttpController httpController, Map<String, String> params) {
            try {
                return httpController.doDelelete(params);
            } catch (HttpStatusCodeException ex) {
                return ex.errorResponse();
            }
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

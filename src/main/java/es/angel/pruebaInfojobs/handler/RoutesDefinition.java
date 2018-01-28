package es.angel.pruebaInfojobs.handler;

import es.angel.pruebaInfojobs.controller.*;
import es.angel.pruebaInfojobs.exception.NotFoundException;

import java.util.Arrays;

public enum RoutesDefinition {

    LOGIN("", LoginController.class) {
        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public HttpController controller() throws IllegalAccessException, InstantiationException {
            return this.controller.newInstance();
        }
    }, PAGE_1("page1", Page1Controller.class) {
        @Override
        public boolean isSecure() {
            return true;
        }

        @Override
        public HttpController controller() throws IllegalAccessException, InstantiationException {
            return this.controller.newInstance();
        }
    }, PAGE_2("page2", Page2Controller.class) {
        @Override
        public boolean isSecure() {
            return true;
        }

        @Override
        public HttpController controller() throws IllegalAccessException, InstantiationException {
            return this.controller.newInstance();
        }

    }, PAGE_3("page3", Page3Controller.class) {
        @Override
        public boolean isSecure() {
            return true;
        }

        @Override
        public HttpController controller() throws IllegalAccessException, InstantiationException {
            return this.controller.newInstance();
        }
    }, USERS("users", UsersController.class) {
        @Override
        public boolean isSecure() {
            return true;
        }

        @Override
        public HttpController controller() throws IllegalAccessException, InstantiationException {
            return this.controller.newInstance();
        }
    }, LOGOUT("logout", LogoutController.class) {
        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public HttpController controller() throws IllegalAccessException, InstantiationException {
            return this.controller.newInstance();
        }
    }, ERROR("error", ErrorController.class) {
        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public HttpController controller() throws IllegalAccessException, InstantiationException {
            return this.controller.newInstance();
        }
    };


    public abstract boolean isSecure();

    public abstract HttpController controller() throws IllegalAccessException, InstantiationException;

    private final String path;

    final Class<? extends HttpController> controller;

    RoutesDefinition(String path, Class<? extends HttpController> controller) {
        this.path = path;
        this.controller = controller;
    }

    public static RoutesDefinition obtainDefinition(final String pathDestination) {
        return Arrays.stream(RoutesDefinition.values())
                .filter(rd -> rd.path.equals(pathDestination))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}

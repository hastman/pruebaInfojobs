package es.angel.pruebaInfojobs.handler;

import es.angel.pruebaInfojobs.exception.NotFoundException;

import java.util.Arrays;

public enum RoutesDefinition {

    LOGIN("") {
        @Override
        public boolean isSecure() {
            return false;
        }
    }, PAGE_1("page1") {
        @Override
        public boolean isSecure() {
            return true;
        }
    }, PAGE_2("page2") {
        @Override
        public boolean isSecure() {
            return true;
        }
    }, PAGE_3("page3") {
        @Override
        public boolean isSecure() {
            return true;
        }
    }, USERS("users") {
        @Override
        public boolean isSecure() {
            return true;
        }
    }, LOGOUT("logout") {
        @Override
        public boolean isSecure() {
            return false;
        }
    }, ERROR("error") {
        @Override
        public boolean isSecure() {
            return false;
        }
    };


    public abstract boolean isSecure();

    private final String path;

    RoutesDefinition(String path) {
        this.path = path;
    }

    public static RoutesDefinition obtainDefinition(final String pathDestination) {
        return Arrays.asList(RoutesDefinition.values())
                .stream()
                .filter(rd -> rd.path.equals(pathDestination))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}

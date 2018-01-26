package es.angel.pruebaInfojobs.model;

import java.util.List;

public class Authorization {

    private List<String> paths;
    private List<String> methods;

    private static final String FULL_ACCESS = "*";

    public Authorization(List<String> paths, List<String> methods) {
        this.paths = paths;
        this.methods = methods;
    }

    public List<String> getPaths() {
        return paths;
    }

    public List<String> getMethods() {
        return methods;
    }

    public boolean isMethodAuthorized(String method) {
        return getMethods().contains(method) || FULL_ACCESS.equals(method);
    }

    public boolean isPathAuthorized(String path) {
        return getPaths().contains(path) || FULL_ACCESS.equals(path);
    }
}

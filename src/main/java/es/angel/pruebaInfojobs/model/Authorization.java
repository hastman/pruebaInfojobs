package es.angel.pruebaInfojobs.model;

import java.util.List;

public class Authorization {

    private String path;
    private List<String> methods;

    private static final String FULL_ACCESS = "*";

    public Authorization(String path, List<String> methods) {
        this.path = path;
        this.methods = methods;
    }


    public boolean isMethodAuthorized(String method) {
        return this.methods.contains(method) || FULL_ACCESS.equals(method);
    }

    public boolean isPathAuthorized(String path) {
        return this.path.equals(path) || FULL_ACCESS.equals(path);
    }
}

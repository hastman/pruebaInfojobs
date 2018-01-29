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
        return this.methods.contains(method.toLowerCase()) || this.methods.contains(FULL_ACCESS);
    }

    public boolean isPathAuthorized(String path) {
        return this.path.equals(path) || this.path.equals(FULL_ACCESS);
    }
}

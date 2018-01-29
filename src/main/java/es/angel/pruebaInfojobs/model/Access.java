package es.angel.pruebaInfojobs.model;

import es.angel.pruebaInfojobs.dao.AccessDao;
import es.angel.pruebaInfojobs.dao.BaseDao;
import es.angel.pruebaInfojobs.exception.NotFoundException;

import java.util.List;

public class Access implements KeyModel {

    private List<Authorization> authorizations;

    private String role;

    private BaseDao<Access, String> accessDao;

    public Access() {
        accessDao = new AccessDao();
    }

    public Access(List<Authorization> authorizations, String role) {
        this.authorizations = authorizations;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getKey() {
        return role;
    }

    public Access findByRole() {
        return accessDao.findOne(role).orElseThrow(NotFoundException::new);
    }


    public boolean canAccess(final String method, final String path) {
        return authorizations.stream()
                .anyMatch(auth -> auth.isMethodAuthorized(method) && auth.isPathAuthorized(path));
    }

}
package es.angel.pruebaInfojobs.model;

import es.angel.pruebaInfojobs.dao.AccessDao;
import es.angel.pruebaInfojobs.dao.BaseDao;
import es.angel.pruebaInfojobs.exception.ForbiddenException;
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

    @Override
    public String getKey() {
        return role;
    }

    public Access findByRole(String role) {
        return accessDao.findOne(role).orElseThrow(NotFoundException::new);
    }


    public void canAccess(final String method, final String path) {
        authorizations.stream()
                .filter(auth -> auth.isMethodAuthorized(method) && auth.isPathAuthorized(path))
                .findFirst()
                .orElseThrow(ForbiddenException::new);
    }

}
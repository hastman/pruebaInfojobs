package es.angel.pruebaInfojobs.model;

import es.angel.pruebaInfojobs.dao.SessionDao;
import es.angel.pruebaInfojobs.exception.UnAuthorizedException;

import java.time.LocalDateTime;

public class Session implements KeyModel {

    private String token;
    private LocalDateTime createdDate;

    private SessionDao sessionDao;

    public Session() {
        sessionDao = new SessionDao();
    }

    @Override
    public String getKey() {
        return token;
    }

    public Session findSession() {
        return sessionDao.findOne(token).orElseThrow(UnAuthorizedException::new);
    }

    public void isValidSession() {
        if (createdDate.plusMinutes(5).isAfter(LocalDateTime.now())) {
            sessionDao.delete(token);
            throw new UnAuthorizedException();
        }
    }
}

package es.angel.pruebaInfojobs.model;

import es.angel.pruebaInfojobs.dao.SessionDao;

import java.time.LocalDateTime;
import java.util.Optional;

public class Session implements KeyModel {

    private String userName;


    private LocalDateTime createdDate;

    private SessionDao sessionDao;

    public Session(String userName) {
        this.userName = userName;
        this.sessionDao = new SessionDao();
    }

    public Session() {
        sessionDao = new SessionDao();
    }

    @Override
    public String getKey() {
        return userName;
    }

    public Optional<Session> hasSession() {
        return sessionDao.findOne(userName);
    }

    public void saveSession() {
        if (hasSession().isPresent()) {
            sessionDao.delete(userName);
        }
        this.createdDate = LocalDateTime.now();
        sessionDao.save(this);
    }

    public boolean isValidSession() {
        if (createdDate.plusMinutes(5).isAfter(LocalDateTime.now())) {
            sessionDao.delete(userName);
            return false;
        }
        return true;
    }
}

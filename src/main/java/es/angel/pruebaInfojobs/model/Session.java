package es.angel.pruebaInfojobs.model;

import es.angel.pruebaInfojobs.dao.SessionDao;

import java.time.LocalDateTime;
import java.util.Optional;

public class Session implements KeyModel {

    private String token;
    private String userName;
    private LocalDateTime createdDate;

    private SessionDao sessionDao;

    public Session(String token, String userName) {
        this.token = token;
        this.userName = userName;
        this.sessionDao = new SessionDao();
    }

    public Session(String token) {
        this.token = token;
        this.sessionDao = new SessionDao();
    }

    @Override
    public String getKey() {
        return token;
    }

    public String getUserName() {
        return userName;
    }

    public Optional<Session> hasSession() {
        return sessionDao.findOne(token);
    }

    public void saveSession() {
        if (hasSession().isPresent()) {
            sessionDao.delete(token);
        }
        this.createdDate = LocalDateTime.now();
        sessionDao.save(this);
    }

    public boolean isValidSession() {
        if (LocalDateTime.now().minusMinutes(5).isAfter(createdDate)) {
            sessionDao.delete(token);
            return false;
        }
        return true;
    }
}

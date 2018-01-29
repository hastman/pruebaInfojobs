package es.angel.pruebaInfojobs.model;

import es.angel.pruebaInfojobs.dao.BaseDao;
import es.angel.pruebaInfojobs.dao.UserDao;
import es.angel.pruebaInfojobs.exception.BadRequestException;
import es.angel.pruebaInfojobs.helper.CryptoHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class Users implements KeyModel {

    private List<String> roles;

    private String userName;

    private String password;

    private BaseDao<Users, String> userDao;

    public Users() {
        this.userDao = new UserDao();
    }

    public Users(List<String> roles, String userName, String password) {
        this.roles = roles;
        this.userName = userName;
        this.password = password;
        this.userDao = new UserDao();
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return CryptoHelper.hashData(this.password);
    }


    public void save() {
        userDao.save(this);
    }

    public List<Users> readAll() {
        return userDao.readAll();
    }

    public void delete() {
        userDao.delete(userName);
    }

    public static Users fromJson(Map<String, String> jsonUser) {

        if (!jsonUser.containsKey("userName") || !jsonUser.containsKey("password") || !jsonUser.containsKey("roles")) {
            throw new BadRequestException();
        }
        return new Users.Builder()
                .withPassword(jsonUser.get("password"))
                .withUserName(jsonUser.get("userName"))
                .withRoles(Arrays.asList(jsonUser.get("roles")
                        .split(","))).build();

    }

    public Boolean isUser() {
        final Users userInBD = userDao.findOne(userName).orElseGet(Users::new);
        return userInBD.getPassword().equals(this.getPassword());
    }

    public Users findByUserName() {
        return userDao.findOne(userName).orElseGet(Users::new);
    }

    @Override
    public String toString() {
        return "userName:" + userName + ":roles:" + roles.stream().collect(joining(","));
    }

    @Override
    public String getKey() {
        return userName;
    }

    public void update(String oldUserName) {
        userDao.delete(oldUserName);
        userDao.save(this);
    }


    public static class Builder {

        private List<String> roles;

        private String userName;

        private String password;

        public Builder withRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Users build() {
            return new Users(roles, userName, password);
        }
    }
}
package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.exception.BadRequestException;
import es.angel.pruebaInfojobs.exception.NotFoundException;
import es.angel.pruebaInfojobs.model.Response;
import es.angel.pruebaInfojobs.model.Users;
import es.angel.pruebaInfojobs.viewresolver.RestViewResolver;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsersController extends HttpController {

    public UsersController() {
        this.viewResolver = new RestViewResolver();
    }

    @Override
    public Response doPost(final Map<String, String> parameters) {

        if (parameters.get("PATH_QUERY").length() > 0) {
            throw new NotFoundException();
        }

        final Users users = Users.fromJson(parameters);
        users.save();
        final String accept = parameters.getOrDefault("Accept", "application/json");
        return new Response.Builder()
                .withStatusCode(201)
                .withBody("created")
                .withContentType(accept).build();
    }

    @Override
    public Response doGet(final Map<String, String> parameters) {
        String userName = parameters.get("PATH_QUERY");
        final String accept = parameters.getOrDefault("Accept", "application/json");
        String userData;
        if (userName != null && userName.trim().length() > 0) {
            final Users user = new Users.Builder().withUserName(userName).build().findByUserName();
            if (user.getUserName() == null) {
                throw new NotFoundException();
            }
            userData = user.toString();
        } else {
            final List<Users> users = new Users().readAll();
            userData = users.stream().map(Users::toString).collect(Collectors.joining(":"));
        }
        final String body = viewResolver.resolveView(accept, userData.split(":"));
        return new Response.Builder().withContentType(accept).withBody(body).withStatusCode(200).build();
    }

    @Override
    public Response doDelete(final Map<String, String> parameters) {
        final String userName = obtainUserNameFromPath(parameters);
        new Users.Builder().withUserName(userName).build().delete();
        return new Response.Builder().withStatusCode(204).withBody("").build();
    }

    @Override
    public Response doPut(final Map<String, String> parameters) {
        final String userName = obtainUserNameFromPath(parameters);
        Users.fromJson(parameters).update(userName);
        final String accept = parameters.getOrDefault("Accept", "application/json");
        return new Response.Builder().withStatusCode(200).withContentType(accept).withBody("updated").build();
    }

    private String obtainUserNameFromPath(Map<String, String> parameters) {
        final String userName = parameters.get("PATH_QUERY");
        if (userName == null || userName.trim().length() == 0) {
            throw new BadRequestException();
        }
        return userName;
    }
}

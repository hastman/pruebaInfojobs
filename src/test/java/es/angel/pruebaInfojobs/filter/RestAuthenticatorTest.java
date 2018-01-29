package es.angel.pruebaInfojobs.filter;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import es.angel.pruebaInfojobs.exception.ForbiddenException;
import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class RestAuthenticatorTest {


    final RestAuthenticator restAuthenticator = new RestAuthenticator("users");
    final HttpExchange httpExchange = mock(HttpExchange.class);

    @Test
    public void when_not_exists_basic_auth_header_should_put_sec_un_authorized_exception() {
        when(httpExchange.getRequestHeaders()).thenReturn(new Headers());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        restAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute(anyString(), any(UnAuthorizedException.class));
    }

    @Test
    public void when_exists_basic_auth_header_and_is_invalid_should_put_sec_un_authorized_exception() {
        when(httpExchange.getRequestHeaders()).thenReturn(headerWithFalseAuth());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        restAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute(anyString(), any(UnAuthorizedException.class));
    }

    @Test
    public void when_exists_basic_auth_header_and_is_valid_but_cannot_access_should_put_sec_un_forbidden_exception() throws URISyntaxException {
        when(httpExchange.getRequestHeaders()).thenReturn(headerWithAuth());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/users"));
        when(httpExchange.getRequestMethod()).thenReturn("Post");
        restAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute(anyString(), any(ForbiddenException.class));
    }

    @Test
    public void when_exists_basic_auth_header_and_is_valid_and_can_access_should_put_user_data() throws URISyntaxException {
        when(httpExchange.getRequestHeaders()).thenReturn(headerWithAuth());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/users"));
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        restAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute("USER_IN_SESSION", "user1");
    }

    private Headers headerWithAuth() {
        final Headers headers = new Headers();
        headers.add("Authorization","Basic dXNlcjE6cHdkMQ==");
        return headers;
    }

    private Headers headerWithFalseAuth() {
        final Headers headers = new Headers();
        headers.add("Authorization","Basic YWRtaW46aGZqaGdmaGo=");
        return headers;
    }

}
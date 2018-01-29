package es.angel.pruebaInfojobs.filter;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import es.angel.pruebaInfojobs.exception.ForbiddenException;
import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import es.angel.pruebaInfojobs.infrastructure.InMemoryDatabase;
import es.angel.pruebaInfojobs.model.Session;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class WebAuthenticatorTest {

    private WebAuthenticator webAuthenticator = new WebAuthenticator();
    final HttpExchange httpExchange = mock(HttpExchange.class);
    private final static String UUID_SESSION = UUID.randomUUID().toString();

    @Test
    public void when_not_exists_cookie_with_session_token_should_put_sec_un_authorized_exception() throws URISyntaxException {

        when(httpExchange.getRequestHeaders()).thenReturn(new Headers());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/page1"));
        webAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute(anyString(), any(UnAuthorizedException.class));

    }

    @Test
    public void when_exists_cookie_without_session_should_put_sec_un_authorized_exception() throws URISyntaxException {

        when(httpExchange.getRequestHeaders()).thenReturn(headers());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/page1"));
        webAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute(anyString(), any(UnAuthorizedException.class));

    }

    @Test
    public void when_exists_cookie_with_session_and_is_invalid_should_put_sec_un_authorized_exception() throws URISyntaxException {

        InMemoryDatabase.getInstance().write("sessions", new Session(UUID_SESSION, "user1", LocalDateTime.now().minusMinutes(6)));
        when(httpExchange.getRequestHeaders()).thenReturn(headers());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/page1"));
        webAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute(anyString(), any(UnAuthorizedException.class));
        InMemoryDatabase.getInstance().erase("sessions",UUID_SESSION);

    }

    @Test
    public void when_exists_cookie_with_session_and_is_valid_but_cannot_access_should_put_sec_forbidden_exception() throws URISyntaxException {

        InMemoryDatabase.getInstance().write("sessions", new Session(UUID_SESSION, "user1", LocalDateTime.now().plusMinutes(1)));
        when(httpExchange.getRequestHeaders()).thenReturn(headers());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/page2"));
        when(httpExchange.getRequestMethod()).thenReturn("Get");
        webAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute(anyString(), any(ForbiddenException.class));
        InMemoryDatabase.getInstance().erase("sessions",UUID_SESSION);
    }

    @Test
    public void when_exists_cookie_with_session_and_is_valid_and_can_access_should_put_user_data() throws URISyntaxException {

        InMemoryDatabase.getInstance().write("sessions", new Session(UUID_SESSION, "user1", LocalDateTime.now().plusMinutes(1)));
        when(httpExchange.getRequestHeaders()).thenReturn(headers());
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/page1"));
        when(httpExchange.getRequestMethod()).thenReturn("Get");
        webAuthenticator.authenticate(httpExchange);
        verify(httpExchange).setAttribute("USER_IN_SESSION", "user1");
        InMemoryDatabase.getInstance().erase("sessions",UUID_SESSION);
    }


    private Headers headers() {
        Headers headers = new Headers();
        headers.add("Cookie", "session=" + UUID_SESSION);
        return headers;


    }

}

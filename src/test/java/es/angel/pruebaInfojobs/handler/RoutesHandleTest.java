package es.angel.pruebaInfojobs.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import es.angel.pruebaInfojobs.fakeobjects.FakeOutputStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import static org.mockito.Mockito.*;

public class RoutesHandleTest {


    private final RoutesHandle routesHandle = new RoutesHandle();
    private final HttpExchange httpExchange = mock(HttpExchange.class);

    @Test
    public void when_route_not_exists_should_generate_not_found_response() throws IOException {
        when(httpExchange.getRequestURI()).thenReturn(URI.create("http://localhost/pathNotFound"));
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getResponseBody()).thenReturn(new FakeOutputStream());
        routesHandle.handle(httpExchange);
        verify(httpExchange).sendResponseHeaders(404, 0);
    }

    @Test
    public void when_route_exists_but_method_is_not_supported_should_generate_method_not_supported_response() throws IOException {
        when(httpExchange.getRequestURI()).thenReturn(URI.create("http://localhost/page1"));
        when(httpExchange.getRequestMethod()).thenReturn("POST");
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getResponseBody()).thenReturn(new FakeOutputStream());
        routesHandle.handle(httpExchange);
        verify(httpExchange).sendResponseHeaders(405, 0);
    }

    @Test
    public void when_rout_exists_and_method_is_correct_should_generate_ok_response() throws IOException {
        when(httpExchange.getRequestURI()).thenReturn(URI.create("http://localhost/page1"));
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getResponseBody()).thenReturn(new FakeOutputStream());
        routesHandle.handle(httpExchange);
        verify(httpExchange).sendResponseHeaders(200, 0);
    }

    @Test
    public void when_exists_sec_excepion_should_be_generate_response_with_error_code() throws IOException {
        when(httpExchange.getAttribute("SEC_EXCEPTION")).thenReturn(new UnAuthorizedException());
        when(httpExchange.getRequestURI()).thenReturn(URI.create("http://localhost/page1"));
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("test".getBytes()));
        when(httpExchange.getResponseBody()).thenReturn(new FakeOutputStream());
        routesHandle.handle(httpExchange);
        verify(httpExchange).sendResponseHeaders(401, 0);
    }


}
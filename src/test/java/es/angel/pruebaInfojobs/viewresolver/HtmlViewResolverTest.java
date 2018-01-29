package es.angel.pruebaInfojobs.viewresolver;

import es.angel.pruebaInfojobs.exception.InternalError;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class HtmlViewResolverTest {


    private HtmlViewResolver viewResolver = new HtmlViewResolver();

    @Test(expected = InternalError.class)
    public void when_try_to_resolve_view_and_not_exists_should_throw_internal_error() {
        viewResolver.resolveView("notFoundTemplate", "message");
    }

    @Test
    public void when_resolve_view_with_message_should_be_expected_view() {
        final String resolvedView = viewResolver.resolveView("html/testResolver.html", "prueba");
        final String expectedView = obtainExpectedView("html/assertTestResolver.html");
        assertThat(expectedView, is(resolvedView));
    }

    @Test
    public void when_resolve_view_without_replace_message_should_return_the_same_view(){
        final String resolvedView = viewResolver.resolveView("html/fileWithoutReplace.html", null);
        final String expectedView = obtainExpectedView("html/fileWithoutReplace.html");
        assertThat(expectedView, is(resolvedView));
    }

    private String obtainExpectedView(String view) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource(view).toURI()));
            return lines.stream().collect(Collectors.joining("\n"));
        } catch (IOException | URISyntaxException excetion) {
            fail();
            return "";
        }
    }

}
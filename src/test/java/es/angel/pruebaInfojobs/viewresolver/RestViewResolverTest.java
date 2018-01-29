package es.angel.pruebaInfojobs.viewresolver;

import org.junit.Test;

import static org.junit.Assert.*;

public class RestViewResolverTest {

    final RestViewResolver restViewResolver = new RestViewResolver();

    @Test
    public void when_resolve_view_with_json_format_should_return_expected_json() {

        final String resolveView = restViewResolver
                .resolveView("application/json", "test", "tes1", "test2", "test2", "test3", "test4");
        final String expected = "{\"test\":\"tes1\",\"test2\":\"test2\",\"test3\":\"test4\"}";
        assertEquals(resolveView, expected);

    }


    @Test
    public void when_resolve_view_with_json_with_array_format_should_return_expected_json() {

        final String resolveView = restViewResolver
                .resolveView("application/json", "test", "tes1", "test2", "test2", "test3", "test,test4");
        final String expected = "{\"test\":\"tes1\",\"test2\":\"test2\",\"test3\":[\"test\",\"test4\"]}";
        assertEquals(resolveView, expected);

    }

    @Test
    public void when_resolve_view_with_xml_format_should_return_expected_xml() {
        final String resolveView = restViewResolver
                .resolveView("application/xml", "test", "tes1", "test2", "test2", "test3", "test4");
        final String expected = "<test>tes1</test><test2>test2</test2><test3>test4</test3>";
        assertEquals(resolveView, expected);
    }

    @Test
    public void when_resolve_view_with_xml_array_format_should_return_expected_xml() {
        final String resolveView = restViewResolver
                .resolveView("application/xml", "test", "tes1", "test2", "test2", "test3", "test,test4");
        final String expected = "<test>tes1</test><test2>test2</test2><test3s><test3>test</test3><test3>test4</test3></test3s>";
        assertEquals(resolveView, expected);
    }


}
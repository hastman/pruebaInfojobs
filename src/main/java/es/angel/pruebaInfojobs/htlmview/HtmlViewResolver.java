package es.angel.pruebaInfojobs.htlmview;

import es.angel.pruebaInfojobs.exception.InternalError;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class HtmlViewResolver implements ViewResolver {


    public String resolveView(String template, String... message) {
        try (Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource(template).toURI()))) {
            return lines.map(line -> String.format(line, Optional.ofNullable(message)
                    .map(m -> m[0])
                    .orElse("")))
                    .collect(joining("\n"));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            System.err.println("Error al resolver la vista");
            throw new InternalError();
        }
    }

}

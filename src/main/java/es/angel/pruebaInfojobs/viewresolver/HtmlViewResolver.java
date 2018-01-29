package es.angel.pruebaInfojobs.viewresolver;

import es.angel.pruebaInfojobs.exception.InternalError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class HtmlViewResolver implements ViewResolver {


    public String resolveView(String template, String... message) {
        try (Stream<String> lines = linesForImputStream(ClassLoader.getSystemResourceAsStream(template)).stream()) {
            return lines.map(line -> String.format(line, Optional.ofNullable(message)
                    .map(m -> m[0])
                    .orElse("")))
                    .collect(joining("\n"));
        } catch (IOException | NullPointerException e) {
            System.err.println("Error al resolver la vista");
            throw new InternalError();
        }
    }

    private List<String> linesForImputStream(InputStream inputStream)
            throws IOException {
        final List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}

package es.angel.pruebaInfojobs.helper;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ParamsHelper {


    public static Map<String, String> extractParams(HttpExchange httpExchange) {
        final String body = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()))
                .lines().collect(Collectors.joining("\n"));
        final Map<String, String> extracted = new HashMap<>();
        extracted.put("PATH_QUERY", extractFromPath(httpExchange.getRequestURI().getPath()));
        extracted.put("USER_IN_SESSION", String.valueOf(httpExchange.getAttribute("USER_IN_SESSION")));
        extracted.putAll(extractFromHeaders(httpExchange.getRequestHeaders()));
        extracted.putAll(extractFromBody(body));
        extracted.putAll(extractJsonBody(body));
        return extracted;
    }

    private static Map<String, String> extractFromBody(String body) {
        final Map<String, String> bodyContent = new HashMap<>();
        Stream.of(body.split("&")).forEach(bodyParm -> {
            final String[] keyValue = bodyParm.split("=");
            if (keyValue.length == 2) {
                bodyContent.put(keyValue[0], keyValue[1]);
            }
        });
        return bodyContent;
    }

    private static Map<String, String> extractJsonBody(String body) {
        if (body.startsWith("{") && body.endsWith("}")) {
            return Stream.of(body.replaceAll("[{}\\[\\]\"]", "").split(","))
                    .collect(Collectors.toMap(s -> s.split(":")[0].trim(), s -> s.split(":")[1].trim()));
        }
        return new HashMap<>();
    }

    private static Map<String, String> extractFromHeaders(Headers responseHeaders) {
        final Map<String, String> headersContent = new HashMap<>();
        responseHeaders.forEach((key, value) -> headersContent.put(key, value.get(0)));
        return headersContent;
    }

    private static String extractFromPath(String path) {
        final List<String> paths = Stream.of(path.split("/")).filter(p -> p.length() > 0).collect(toList());
        if (paths.size() > 1) {
            return paths.get(1);
        }
        return "";
    }

}

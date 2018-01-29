package es.angel.pruebaInfojobs.viewresolver;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class RestViewResolver implements ViewResolver {
    @Override
    public String resolveView(String template, String... message) {
        if (template.equals("application/xml")) {
            return xmlView(message);
        }
        return jsonView(message);
    }

    private String jsonView(String... message) {
        final StringBuilder jsonView = new StringBuilder().append("{");
        int j = 0;
        for (int i = 0, messageLength = message.length; i < messageLength; i += 2) {
            jsonView.append("\"").append(message[j]).append("\"").append(":");
            final String value = message[++j];
            if (value.contains(",")) {
                jsonView.append("[")
                        .append(Stream.of(value.split(",")).map(v -> "\"" + v + "\"").collect(joining(",")))
                        .append("]");
            } else {
                jsonView.append("\"").append(value).append("\"");
            }
            if (j < messageLength - 1) {
                jsonView.append(",");
            }
            j++;
        }


        return jsonView.append("}").toString();
    }

    private String xmlView(String... message) {
        final StringBuilder xmlView = new StringBuilder();
        int j = 0;
        for (int i = 0, messageLength = message.length; i < messageLength; i += 2) {
            final String label = message[j];
            String content = message[++j];
            if (content.contains(",")) {
                xmlView.append("<").append(label).append("s>");
                xmlView.append(Stream.of(content.split(","))
                        .map(c -> new StringBuilder().append("<").append(label).append(">")
                                .append(c)
                                .append("</").append(label).append(">").toString()).collect(joining("")));
                xmlView.append("</").append(label).append("s>");
            } else {
                xmlView.append("<").append(label).append(">")
                        .append(content)
                        .append("</").append(label).append(">");
            }
            j++;
        }
        return xmlView.toString();
    }
}

package es.angel.pruebaInfojobs.htlmview;

public interface HtmlView {

    static final String HTML_TEMPLATE = "<html><head><title>%s</title><body><table><tr><td>%s</td></tr><tr><td>%s</td></tr></body></html>";

    String generateView(String viewContent);
}

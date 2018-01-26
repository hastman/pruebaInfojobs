package es.angel.pruebaInfojobs.htlmview;

public class PageHtmlView implements HtmlView {

    @Override
    public String generateView(String viewContent) {

        final String title = "Page";
        final String logOut = "<a href='/logout'>Salir</a>";
        return String.format(HTML_TEMPLATE, title, viewContent, logOut);
    }
}

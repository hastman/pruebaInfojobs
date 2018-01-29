package es.angel.pruebaInfojobs.viewresolver;

public interface ViewResolver {

    String resolveView(String template, String... message);

}

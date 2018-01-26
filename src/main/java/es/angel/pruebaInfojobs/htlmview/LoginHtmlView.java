package es.angel.pruebaInfojobs.htlmview;

public class LoginHtmlView implements HtmlView {

    @Override
    public String generateView(String viewContent) {

        final String title = "Login Form";

        final String loginForm = new StringBuffer("<form>").append("<label>User:</label>").append("<input type='text' id='user' name='user'/>").append("</br>")
                .append("<label>Password:</label>").append("<input type='password' id='pass' name='pass'/>").append("</br>")
                .append("<input type='submit' value='Submit'/>").toString();

        return String.format(HTML_TEMPLATE, title, loginForm, "");

    }

}

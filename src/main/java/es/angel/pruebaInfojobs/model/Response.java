package es.angel.pruebaInfojobs.model;

public class Response {

    private Integer statusCode;
    private String bodyContent;
    private String contentType;

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public String getContentType() {
        return contentType;
    }

    Response(Integer statusCode, String bodyContent, String contentType) {
        this.statusCode = statusCode;
        this.bodyContent = bodyContent;
        this.contentType = contentType;
    }

    public static class Builder {
        private Integer statusCode;
        private String bodyContent;
        private String contentType;


        public Builder withStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withBody(String bodyContent) {
            this.bodyContent = bodyContent;
            return this;
        }

        public Builder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Response build() {
            return new Response(statusCode, bodyContent, contentType);
        }
    }
}

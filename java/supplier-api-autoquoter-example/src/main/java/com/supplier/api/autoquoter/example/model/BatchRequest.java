package com.supplier.api.autoquoter.example.model;

public class BatchRequest {

    private String method;//http method
    private String resource;//api resource
    private Object body;
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public Object getBody() {
        return body;
    }
    public void setBody(Object body) {
        this.body = body;
    }
}

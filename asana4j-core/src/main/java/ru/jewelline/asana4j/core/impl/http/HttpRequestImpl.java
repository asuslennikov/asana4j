package ru.jewelline.asana4j.core.impl.http;

import ru.jewelline.asana4j.http.HttpMethod;
import ru.jewelline.asana4j.http.HttpRequest;
import ru.jewelline.asana4j.http.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestImpl<O extends OutputStream> implements HttpRequest<O> {

    private final HttpMethod httpMethod;
    private final HttpClientImpl httpClient;

    private String url;
    private Map<String, String> headers;
    private InputStream entityStream;
    private O destinationStream;

    public HttpRequestImpl(HttpMethod httpMethod, HttpClientImpl httpClient) {
        this.httpMethod = httpMethod;
        this.httpClient = httpClient;
    }

    public void setEntityStream(InputStream entityStream) {
        this.entityStream = entityStream;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    @Override
    public InputStream getRequestBody() {
        return this.entityStream;
    }

    public O getDestinationStream() {
        return this.destinationStream;
    }

    @Override
    public HttpResponse<O> send() {
        return this.sendAndReadResponse(null);
    }

    @Override
    public HttpResponse<O> sendAndReadResponse(O destinationStream) {
        this.destinationStream = destinationStream;
        return this.httpClient.execute(this, HttpMethodWorker.getWorker(this.httpMethod));
    }
}

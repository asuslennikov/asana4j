package ru.jewelline.asana4j.api;

import ru.jewelline.asana4j.http.HttpMethod;

public interface ApiRequestBuilder<T> {
    String OPTION_PRETTY = "pretty";
    String OPTION_METHOD = "method";
    String OPTION_FIELDS = "fields";
    String OPTION_EXPAND = "expand";
    String OPTION_JSONP = "jsonp";

    ApiRequestBuilder<T> path(String apiSuffix);

    ApiRequestBuilder<T> setQueryParameter(String parameterKey, String parameterValue);

    ApiRequestBuilder<T> setHeader(String headerKey, String headerValue);

    ApiRequest<T> buildAs(HttpMethod method);
}

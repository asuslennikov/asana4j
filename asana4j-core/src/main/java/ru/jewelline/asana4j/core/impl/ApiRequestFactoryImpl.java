package ru.jewelline.asana4j.core.impl;

import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.ApiRequestFactory;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.HttpRequestFactory;

public class ApiRequestFactoryImpl implements ApiRequestFactory {
    private final HttpRequestFactory httpRequestFactory;

    public ApiRequestFactoryImpl(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }

    @Override
    public HttpRequestBuilder httpRequest() {
        return this.httpRequestFactory.httpRequest();
    }

    @Override
    public ApiRequestBuilder apiRequest(RequestModifier... requestModifiers) {
        return new ApiRequestWithModifiersBuilder(this.httpRequestFactory)
                .withRequestModifiers(requestModifiers);
    }
}

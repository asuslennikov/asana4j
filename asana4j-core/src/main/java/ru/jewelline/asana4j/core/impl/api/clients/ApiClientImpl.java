package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.ApiRequest;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.ModifiersChain;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.ApiEntity;
import ru.jewelline.asana4j.core.impl.api.ApiRequestBuilderImpl;
import ru.jewelline.asana4j.core.impl.api.entity.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public abstract class ApiClientImpl<AT, T extends ApiEntity<AT>> implements ApiEntityInstanceProvider<T> {

    private final AuthenticationService authenticationService;
    private final HttpClient httpClient;

    public ApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        this.authenticationService = authenticationService;
        this.httpClient = httpClient;
    }

    protected AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }

    public ApiRequestBuilder<AT> newRequest() {
        return newRequest((RequestModifier[]) null);
    }

    protected ApiRequestBuilder<AT> newRequest(RequestModifier... requestModifiers) {
        return new ApiRequestWithModifiersBuilder<AT, T>(getAuthenticationService(), getHttpClient(), this).withRequestModifiers(requestModifiers);
    }

    public abstract T newInstance();

    private static final class ApiRequestWithModifiersBuilder<AT, T extends ApiEntity<AT>> extends ApiRequestBuilderImpl<AT, T> {

        private RequestModifier[] requestModifiers;

        public ApiRequestWithModifiersBuilder(AuthenticationService authenticationService, HttpClient httpClient, ApiEntityInstanceProvider<T> apiInstanceProvider) {
            super(authenticationService, httpClient, apiInstanceProvider);
        }

        protected ApiRequestBuilder<AT> withRequestModifiers(RequestModifier[] requestModifiers) {
            this.requestModifiers = requestModifiers;
            return this;
        }

        @Override
        public ApiRequest<AT> buildAs(HttpMethod method) {
            ModifiersChain modifiersChain = new ModifiersChain(this.requestModifiers);
            modifiersChain.next(this, method);
            ApiRequestBuilder<AT> requestBuilder = modifiersChain.getRequestBuilder();
            HttpMethod httpMethod = modifiersChain.getHttpMethod();
            return requestBuilder == this ? super.buildAs(httpMethod) : requestBuilder.buildAs(httpMethod);
        }
    }
}

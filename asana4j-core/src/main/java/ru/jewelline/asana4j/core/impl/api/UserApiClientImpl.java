package ru.jewelline.asana4j.core.impl.api;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.UserApiClient;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.options.RequestOption;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.http.HttpClient;
import ru.jewelline.asana4j.http.HttpMethod;

public class UserApiClientImpl extends ApiClientImpl<User, UserImpl> implements UserApiClient {

    public UserApiClientImpl(AuthenticationService authenticationService, HttpClient httpClient) {
        super(authenticationService, httpClient);
    }

    @Override
    public UserImpl newInstance() {
        return new UserImpl();
    }

    @Override
    public User getCurrentUser(RequestOption... requestOptions) {
        return newRequest(requestOptions)
                .path("users/me")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject();
    }

    @Override
    public User getUserById(long userId, RequestOption... requestOptions) {
        return newRequest(requestOptions)
                .path("users/" + userId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject();
    }

    @Override
    public PagedList<User> getUsers(RequestOption... requestOptions) {
        return newRequest(requestOptions)
                .path("users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection();
    }

    @Override
    public PagedList<User> getWorkspaceUsers(long workspaceId, RequestOption... requestOptions) {
        return newRequest(requestOptions)
                .path("workspaces/" + workspaceId + "/users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection();
    }
}

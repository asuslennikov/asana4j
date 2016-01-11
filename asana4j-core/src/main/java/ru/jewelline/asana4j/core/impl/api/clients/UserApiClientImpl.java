package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.UserApiClient;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.http.HttpMethod;

public class UserApiClientImpl extends ApiClientImpl implements UserApiClient {

    public UserApiClientImpl(RequestFactory requestFactory) {
        super(requestFactory);
    }

    private EntityDeserializer<UserImpl> getUserDeserializer() {
        return getEntityContext().getDeserializer(UserImpl.class);
    }

    @Override
    public User getCurrentUser(RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("users/me")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getUserDeserializer());
    }

    @Override
    public User getUserById(long userId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("users/" + userId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getUserDeserializer());
    }

    @Override
    public PagedList<User> getUsers(RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getUserDeserializer());
    }

    @Override
    public PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("workspaces/" + workspaceId + "/users")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getUserDeserializer());
    }
}

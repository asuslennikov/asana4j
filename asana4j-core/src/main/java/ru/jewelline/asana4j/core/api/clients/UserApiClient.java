package ru.jewelline.asana4j.core.api.clients;

import ru.jewelline.asana.common.PagedList;
import ru.jewelline.asana4j.core.api.entity.User;
import ru.jewelline.request.http.modifiers.RequestModifier;

public interface UserApiClient {
    User getCurrentUser(RequestModifier... requestModifiers);
    User getUserById(long userId, RequestModifier... requestModifiers);
    PagedList<User> getUsers(RequestModifier... requestModifiers);
    PagedList<User> getWorkspaceUsers(long workspaceId, RequestModifier... requestModifiers);
}
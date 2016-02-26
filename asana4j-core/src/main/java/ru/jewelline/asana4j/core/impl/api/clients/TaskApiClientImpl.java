package ru.jewelline.asana4j.core.impl.api.clients;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.TaskApiClient;
import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.TagImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TaskImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.http.HttpMethod;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

public class TaskApiClientImpl extends ApiClientImpl implements TaskApiClient {

    public TaskApiClientImpl(HttpRequestFactory httpRequestFactory, ApiEntityContext entityContext) {
        super(HttpRequestFactory, entityContext);
    }

    private EntityDeserializer<TaskImpl> getTaskDeserializer() {
        return getEntityContext().getDeserializer(TaskImpl.class);
    }

    @Override
    public Task getTaskById(long taskId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("tasks/" + taskId)
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getTaskDeserializer());
    }

    @Override
    public PagedList<Task> getTasksForProject(long projectId, RequestModifier... requestModifiers) {
        return newRequest(requestModifiers)
                .setUrl("projects/" + projectId + "/tasks")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getTaskDeserializer());
    }

    @Override
    public void deleteTask(long taskId) {
        newRequest()
                .setUrl("tasks/" + taskId)
                .buildAs(HttpMethod.GET)
                .execute();
    }

    @Override
    public PagedList<Tag> getTaskTags(long taskId, RequestModifier... requestModifiers) {
        return newRequest()
                .setUrl("tasks/" + taskId + "/tags")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getEntityContext().getDeserializer(TagImpl.class));
    }

    @Override
    public void addTag(long taskId, long tagId) {
        getEntityContext().newRequest()
                .setUrl("tasks/" + taskId + "/addTag")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("tag", tagId)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
    }

    @Override
    public void removeTag(long taskId, long tagId) {
        getEntityContext().newRequest()
                .setUrl("tasks/" + taskId + "/removeTag")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("tag", tagId)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
    }
}

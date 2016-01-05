package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.User;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldReader;
import ru.jewelline.asana4j.core.impl.api.entity.common.JsonFieldWriter;
import ru.jewelline.asana4j.core.impl.api.entity.io.SimpleFieldsUpdater;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorkspaceImpl extends ApiEntityImpl<WorkspaceImpl> implements Workspace {

    private long id;
    private String name;
    private boolean organisation;

    public WorkspaceImpl(ApiEntityContext context) {
        super(WorkspaceImpl.class, context);
    }

    @Override
    protected List<JsonFieldReader<WorkspaceImpl>> getFieldReaders() {
        return Arrays.<JsonFieldReader<WorkspaceImpl>>asList(WorkspaceImplProcessor.values());
    }

    @Override
    protected List<JsonFieldWriter<WorkspaceImpl>> getFieldWriters() {
        return Collections.<JsonFieldWriter<WorkspaceImpl>>singletonList(WorkspaceImplProcessor.NAME);
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isOrganisation() {
        return organisation;
    }

    public void setOrganisation(boolean organisation) {
        this.organisation = organisation;
    }

    @Override
    public boolean equals(Object candidate) {
        if (this == candidate) {
            return true;
        }
        if (candidate == null || getClass() != candidate.getClass()) {
            return false;
        }
        return id == ((WorkspaceImpl) candidate).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Workspace [");
        out.append("id = ").append(getId());
        out.append(", name = ").append(getName());
        out.append(", organisation = ").append(isOrganisation());
        out.append(']');
        return out.toString();
    }

    @Override
    public void update() {
        getContext().newRequest()
                .path("workspaces/" + this.getId())
                .setEntity(this)
                .buildAs(HttpMethod.PUT)
                .execute()
                .asApiObject(new ApiEntityDeserializer<>(this));
    }

    @Override
    public User addUser(long userId) {
        return addUserInternal(userId);
    }

    @Override
    public User addUser(String email) {
        return addUserInternal(email != null ? email : "");
    }

    @Override
    public User addCurrentUser() {
        return addUserInternal("me");
    }

    private User addUserInternal(Object userReference) {
        return getContext().newRequest()
                .path("workspaces/" + this.getId() + "/addUser")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("user", userReference.toString())
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getContext().getDeserializer(UserImpl.class));
    }

    @Override
    public void removeUser(long userId) {
        removeUserInternal(userId);
    }

    @Override
    public void removeUser(String email) {
        removeUserInternal(email != null ? email : "");
    }

    @Override
    public void removeCurrentUser() {
        removeUserInternal("me");
    }

    private void removeUserInternal(Object userReference) {
        getContext().newRequest()
                .path("workspaces/" + this.getId() + "/removeUser")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("user", userReference.toString())
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute();
    }

    @Override
    public List<Project> getProjects() {
        // TODO should we update a workspace of project to this instance?
        return getContext().newRequest()
                .path("workspaces/" + this.getId() + "/projects")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getContext().getDeserializer(ProjectImpl.class));
    }

    @Override
    public Project createProject(String name) {
        SimpleFieldsUpdater fieldsUpdater = new SimpleFieldsUpdater()
                .setField("workspace", getId())
                .setField("name", name);
        return getContext().newRequest()
                .path("projects")
                .setEntity(fieldsUpdater.wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getContext().getDeserializer(ProjectImpl.class));
    }

    @Override
    public Task.TaskCreator createTask() {
        return new TaskImplCreator(getContext()).setWorkspace(getId());
    }
}

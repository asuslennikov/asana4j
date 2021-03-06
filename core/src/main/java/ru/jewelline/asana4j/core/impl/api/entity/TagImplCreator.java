package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.Tag.TagCreator;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityResponseReceiver;
import ru.jewelline.request.http.HttpMethod;

public class TagImplCreator extends TagBuilderImpl<TagCreator> implements TagCreator {

    private final ApiEntityContext context;

    public TagImplCreator(ApiEntityContext context) {
        super(TagCreator.class);
        this.context = context;
    }

    @Override
    public TagCreator setWorkspace(long workspaceId) {
        putField(TagImplProcessor.WORKSPACE.getFieldName(), workspaceId);
        return this;
    }

    @Override
    public Tag create() {
        return this.context.newRequest()
                .setUrl("tags")
                .setEntity(wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute(new ApiEntityResponseReceiver())
                .asApiObject(this.context.getDeserializer(TagImpl.class));
    }
}

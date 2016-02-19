package ru.jewelline.asana4j.core.api.entity;

/**
 * An object which has an unique numeric identifier
 */
public interface HasId {

    /**
     * @return unique numeric identifier for the object
     * @api.field <code>id</code>
     * @api.access Read-only
     */
    long getId();
}
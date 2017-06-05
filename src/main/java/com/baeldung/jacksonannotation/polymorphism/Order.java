package com.baeldung.jacksonannotation.polymorphism;

import java.util.UUID;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Order {

    private UUID id;
    private Type type;

    public Order() {
        this.id = UUID.randomUUID();
    }

    public Order(Type type) {
        this();
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

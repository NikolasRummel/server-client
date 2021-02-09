/*
 * Copyright (c) 2020 Nikolas Rummel
 * Created: 15.12.20, 20:33
 * File: Packet.java
 */

package de.nikolas.rpg.networking.packet;

import java.io.Serializable;

public abstract class Packet<T> implements Serializable {

    private T t;
    private String sender;

    public Packet(String sender) {
        this.sender = sender;
    }

    public T value() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public String getSender() {
        return sender;
    }

    public abstract void handle();
}

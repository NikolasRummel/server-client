/*
 * Copyright (c) 2020 Nikolas Rummel
 * Created: 15.12.20, 22:06
 * File: LoginPacket.java
 */

package de.nikolas.rpg.networking.packet.packets;

import de.nikolas.rpg.networking.packet.Packet;

import java.io.Serializable;

public class LoginPacket extends Packet<String> implements Serializable {

    public LoginPacket(String username) {
        super(username);
        set(username);
    }

    @Override
    public void handle() {
        System.out.println("[Packet] Login handle!");
    }
}

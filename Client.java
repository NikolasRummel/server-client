/*
 * Copyright (c) 2020 Nikolas Rummel
 * Created: 15.12.20, 20:33
 * File: Client.java
 */
package de.nikolas.rpg.networking;

import de.nikolas.rpg.networking.packet.Packet;
import de.nikolas.rpg.networking.packet.handler.ListenFromServer;
import de.nikolas.rpg.networking.packet.packets.LoginPacket;
import de.nikolas.rpg.networking.packet.packets.MessagePacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;

    private String hostName;
    private int port;
    private String userName;

    public Client(String hostName, int port, String userName) {
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
    }

    public void start() {
        try {
            socket = new Socket(hostName, port);
        } catch(Exception e) {
            System.out.println("Error connecting to server! ex:" + e);
        }

        System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());

        try {
            inputStream  = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Exception creating new Input/output Streams: " + e);
        }

        new ListenFromServer(this).start();
        login();
    }

    public void login() {
        try {
            System.out.println("sended login packet");
            outputStream.writeObject(new LoginPacket(userName));
        } catch (IOException e) {
            System.out.println("Error doing login! " + e);
            closeConnection();
        }
    }

    public void sendPacket(Packet packet) {
        try {
            outputStream.writeObject(packet);
        } catch (IOException e) {
            System.out.println("Error while sending packet! " + e);
        }
    }

    public void closeConnection() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        }catch (Exception e) {}
    }

    public void readConsole() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        sendPacket(new MessagePacket(userName, input));
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 25585, "Nikolas");
        client.start();
        client.readConsole();
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}

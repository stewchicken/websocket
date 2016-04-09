package org.example.websocket;


import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint gives the relative name for the end point This will be
 * accessed via ws://localhost:8080/EchoServer/echo Where "localhost" is the
 * address of the host, "EchoServer" is the name of the package and "echo" is
 * the address to access this class from the server
 */
@ServerEndpoint("/echo")
public class EchoServer {

    /**
     * @OnOpen allows us to intercept the creation of a new session. The session
     * class allows us to send data to the user. In the method onOpen, we'll let
     * the user know that the handshake was successful.
     */
    static int count = 0;

    public EchoServer() {
        count++;
        System.out.println(" ################## EchoServer instances count : " + count);
    }

    @OnOpen
    public void onOpen(Session session) {
        try {
            session.getBasicRemote().sendText("Connection Established" + "EchoServer instances count : " + count + "sessionID " + session.getId());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the
     * message and allow us to react to it. For now the message is read as a
     * String.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message + "sessionID " + session.getId());
        try {
            session.getBasicRemote().sendText(message + "  " + "EchoServer instances count : " + count + "sessionID " + session.getId());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended" +"EchoServer instances count : " + count);
    }
    
    @Override
    public void finalize() {
        count--;
        System.out.println("destructor");
    }
}

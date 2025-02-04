package de.uniks.pmws2425.nopm.ws;

import kong.unirest.core.json.JSONObject;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@javax.websocket.ClientEndpoint
public class ClientEndpoint {
    private final String endpointURI;
    Session userSession;

    private final List<Consumer<String>> messageHandlers = Collections.synchronizedList(new ArrayList<>());

    public ClientEndpoint(String endpointURI) {
        this.endpointURI = endpointURI;
    }

    public void connect() {
        if (userSession != null && userSession.isOpen()) {
            return;
        }

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            userSession = container.connectToServer(this, new URI(endpointURI));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        for (final Consumer<String> handler : this.messageHandlers) {
            handler.accept(message);
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    public void addMessageHandler(Consumer<String> msgHandler) {
        this.messageHandlers.add(msgHandler);
    }

    public void removeMessageHandler(Consumer<String> msgHandler) {
        this.messageHandlers.remove(msgHandler);
    }

    public void sendEvent(String event, Object data) {
        this.sendMessage(new JSONObject().put("event", event).put("data", data).toString());
    }

    public <T> Runnable subscribe(String event, Consumer<T> callback) {
        this.sendEvent("subscribe", event);
        final Consumer<String> handler = message -> {
            final JSONObject json = new JSONObject(message);
            if (event.equals(json.getString("event"))) {
                callback.accept((T) json.get("data"));
            }
        };
        this.addMessageHandler(handler);
        return () -> {
            this.sendEvent("unsubscribe", event);
            this.removeMessageHandler(handler);
        };
    }

    public void sendMessage(String message) {
        if (this.userSession == null) {
            return;
        }
        this.userSession.getAsyncRemote().sendText(message);
    }

    public void close() {
        if (this.userSession == null) {
            return;
        }

        try {
            this.userSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasMessageHandlers() {
        return !this.messageHandlers.isEmpty();
    }
}

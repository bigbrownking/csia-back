package org.agro.agrohack.config.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> messagesSessions = new ConcurrentHashMap<>();
    private static final Map<String, WebSocketSession> userLvlUpSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userEmail = extractEmail(session);
        if (userEmail != null) {
            messagesSessions.put(userEmail, session);
            log.info("WebSocket connected: {} for user {}", session.getId(), userEmail);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload().trim();
        log.info("Received message from {}: {}", session.getId(), payload);

        String userEmail = extractEmail(session);
        if (userEmail != null && payload.equalsIgnoreCase("subscribe:userLvlUp")) {
            userLvlUpSessions.put(userEmail, session);
            log.info("User {} subscribed to userLvlUp notifications", userEmail);
        } else {
            session.sendMessage(new TextMessage("Unknown command."));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userEmail = extractEmail(session);
        if (userEmail != null) {
            messagesSessions.remove(userEmail);
            userLvlUpSessions.remove(userEmail);
            log.info("WebSocket disconnected: {} for user {}", session.getId(), userEmail);
        }
    }

    public void sendMessageToUser(String userEmail, String message) {
        sendToSession(messagesSessions, userEmail, message);
    }
    public void sendMessageToUsers(String message) {
        sendMessageToAllUsers(message);
    }

    public void sendUserLvlUpMessage(String userEmail, String message) {
        sendToSession(userLvlUpSessions, userEmail, message);
    }

    private void sendToSession(Map<String, WebSocketSession> sessions, String userEmail, String message) {
        WebSocketSession session = sessions.get(userEmail);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            } catch (IOException e) {
                log.error("Error sending message to user: {}", userEmail, e);
            }
        }
    }
    public void sendMessageToAllUsers(String message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            for (WebSocketSession session : messagesSessions.values()) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonMessage));
                }
            }
        } catch (IOException e) {
            log.error("Error sending WebSocket message", e);
        }
    }



    private String extractEmail(WebSocketSession session) {
        return session.getUri() != null ? session.getUri().getQuery().replace("email=", "") : null;
    }
}

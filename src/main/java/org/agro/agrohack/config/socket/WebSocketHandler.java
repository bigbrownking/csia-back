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
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArraySet<WebSocketSession> messagesSessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        messagesSessions.add(session);
        log.info("WebSocket connected: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        log.info("Received message from {}: {}", session.getId(), message.getPayload());
        session.sendMessage(new TextMessage("Hello from server!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        messagesSessions.remove(session);
        log.info("WebSocket disconnected: {}", session.getId());
    }

    public void broadcastNewsMessage(String message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            for (WebSocketSession session : messagesSessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonMessage));
                }
            }
        } catch (IOException e) {
            log.error("Error sending WebSocket message", e);
        }
    }
}

package com.anybodyherechat.config;

import com.anybodyherechat.model.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

/**
 * {@link WebSocketEventListener}
 * Purpose: Custom event listener class implementation
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        if (Objects.nonNull(accessor.getSessionAttributes()) && Objects.nonNull(accessor.getSessionAttributes()
                                                                                        .get("username"))) {
            var username = accessor.getSessionAttributes().get("username").toString();
            var chatMessage = new ChatMessageDTO().sender(username)
                                                  .content(String.format("The user %s has left the chat.", username));
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}

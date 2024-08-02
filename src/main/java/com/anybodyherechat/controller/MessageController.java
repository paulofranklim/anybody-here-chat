package com.anybodyherechat.controller;

import com.anybodyherechat.model.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * {@link MessageController}
 * Purpose: Controller class to connect front with websocket
 */
@Controller
public class MessageController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.userJoin")
    @SendTo("/topic/public")
    public ChatMessageDTO userJoin(@Payload ChatMessageDTO chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        simpMessageHeaderAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setContent(String.format("User %s has joined the chat.", chatMessage.getSender()));
        return chatMessage;
    }
}

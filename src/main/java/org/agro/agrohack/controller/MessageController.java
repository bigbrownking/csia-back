package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.config.socket.WebSocketHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ws")
@Tag(name = "Message Controller",  description = "Endpoints for managing messages using web socket")
public class MessageController {
    private final WebSocketHandler webSocketHandler;

    @GetMapping("/send")
    @Operation(summary = "Send message to web socket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sended successfully"),
    })
    public ResponseEntity<String> sendMessage(
            @RequestParam String message
    ){
        webSocketHandler.broadcastNewsMessage(message);
        return ResponseEntity.ok("Message sent.");
    }
}

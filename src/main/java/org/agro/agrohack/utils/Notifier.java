package org.agro.agrohack.utils;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.config.socket.WebSocketHandler;
import org.agro.agrohack.repository.UserRepository;
import org.agro.agrohack.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Notifier {
    private final UserService userService;
    private final TimeTranslator timeTranslator;
    private final WebSocketHandler webSocketHandler;
    private final String WATERING = "It is watering time!";
    private final String CHECKING = "Assess the state of micro greens today!";

    public void notifyUsersWatering(){
        webSocketHandler.broadcastMessage(WATERING);
    }
    public void notifyUsersChecking(){
        webSocketHandler.broadcastMessage(CHECKING);
    }
}

package org.agro.agrohack.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.config.socket.WebSocketHandler;
import org.agro.agrohack.constants.Watering;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.repository.PlantsRepository;
import org.agro.agrohack.repository.UserRepository;
import org.agro.agrohack.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class Notifier {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PlantsRepository plantsRepository;
    private final TimeTranslator timeTranslator;
    private final WebSocketHandler webSocketHandler;
    private final String WATERING = "It is watering time!";
    private final String CHECKING = "Assess the state of micro greens today!";
    private final String name = "Persistent-Notification-Listener";
    private static final LocalTime MIDDAY = LocalTime.of(12, 0);

    @PostConstruct
    public void init() {
        new Thread(this::listenForever, name).start();
    }

    private void listenForever() {
        while (true) {
            try {
                LocalTime now = LocalTime.now();

                if (now.getHour() == MIDDAY.getHour() && now.getMinute() == MIDDAY.getMinute()) {
                    notifyUsersChecking(CHECKING);
                    Thread.sleep(60000);
                }

                List<UserPlant> userPlants = userService.allUsersPlants();
                for (UserPlant userPlant : userPlants) {
                    Plant plant = plantsRepository.getPlantById(userPlant.getPlantId()).orElseThrow(() -> new NotFoundException("Plant not found..."));

                    for (Watering watering : Watering.values()) {
                        if (plant.getName().equals(watering.getLabel())) {
                            if (userPlant.getLastWateringDate() == null) {
                                userPlant.setLastWateringDate(userPlant.getPlantTime());
                            }
                            int wateringInterval = timeTranslator.parseTimeToDays(watering.getLabel());
                            LocalDateTime lastWatered = userPlant.getLastWateringDate();

                            LocalDateTime nextWateringDate = lastWatered.plusDays(wateringInterval);
                            LocalDateTime nowDateTime = LocalDateTime.now();

                            if (nowDateTime.isBefore(nextWateringDate) &&
                                    nowDateTime.plusHours(1).isAfter(nextWateringDate)) {
                                notifyUsersWatering(userPlant.getEmail());
                            }
                        }
                    }

                }
            } catch (Exception ignored) {

            }
        }
    }

    public void notifyUsersWatering(String email) {
        webSocketHandler.sendMessageToUser(email, WATERING);
    }

    public void notifyUsersChecking(String email) {
        webSocketHandler.sendMessageToUser(email, CHECKING);
    }
}

package org.agro.agrohack.utils;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.constants.Difficulty;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.model.User;
import org.agro.agrohack.repository.PlantsRepository;
import org.agro.agrohack.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelService {
    private final UserRepository userRepository;
    private final PlantsRepository plantsRepository;
    private final int MODERATE_LVL = 5;
    private final int ADVANCED_LVL = 10;
    private final int LVL_BORDER = 100;
    public boolean isEnoughForPlant(String email, String plant_name) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(()->new NotFoundException("User not found..."));

        Plant plant = plantsRepository.getPlantByName(plant_name).orElseThrow(()-> new NotFoundException("Plant not found..."));
        Difficulty difficulty = plant.getDifficulty();
        int userLevel = user.getLevel();

        if (difficulty.equals(Difficulty.EASY)) {
            return true;
        } else if (difficulty.equals(Difficulty.MODERATE)) {
            return userLevel >= MODERATE_LVL;
        } else if (difficulty.equals(Difficulty.ADVANCED)) {
            return userLevel >= ADVANCED_LVL;
        }

        return false;
    }



    public void addExp(String email, int exp) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(()->new NotFoundException("User not found..."));
        user.setExp(user.getExp()+exp);

        if(user.getExp() >= LVL_BORDER){
            lvlUp(email);
            user.setExp(user.getExp()-LVL_BORDER);
        }
        userRepository.save(user);
    }
    private void lvlUp(String email) throws NotFoundException {
        User user = userRepository.getUserByEmail(email).orElseThrow(()->new NotFoundException("User not found..."));
        user.setLevel(user.getLevel()+1);

        userRepository.save(user);
    }
}

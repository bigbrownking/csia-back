package org.agro.agrohack.utils;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.constants.Difficulty;
import org.agro.agrohack.repository.PlantsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ParamUtils {
    public List<String> getAllDifficulties(){
        return Stream.of(Difficulty.values())
                .map(Enum::name)
                .toList();
    }

}

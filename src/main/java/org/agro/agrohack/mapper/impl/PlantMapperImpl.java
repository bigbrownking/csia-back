package org.agro.agrohack.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.mapper.PlantMapper;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.model.PlantCharacteristic;
import org.agro.agrohack.model.plantCharacteristics.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlantMapperImpl implements PlantMapper {

    @Override
    public Plant toPlant(AddPlantRequest addPlantRequest) {
        Plant plant = new Plant();

        plant.setType(addPlantRequest.getType());
        plant.setName(addPlantRequest.getName());
        plant.setDifficulty(addPlantRequest.getDifficulty());

        plant.setCharacteristic(generatePlantCharacteristic(addPlantRequest));
        return plant;
    }

    private PlantCharacteristic generatePlantCharacteristic(AddPlantRequest addPlantRequest){
        PlantCharacteristic plantCharacteristic = new PlantCharacteristic();

        Water water = new Water();
        water.setDifficulty(addPlantRequest.getDifficulty_water());
        water.setWhen(addPlantRequest.getWhen_water());
        water.setTip(addPlantRequest.getTip_water());

        Light light = new Light();
        light.setPreferredLight(addPlantRequest.getPreferredLight_light());
        light.setSuitableWith(addPlantRequest.getSuitableWith_light());

        Toxicity toxicity = new Toxicity();
        toxicity.setPower(addPlantRequest.getPower_toxic());
        toxicity.setToxicTo(addPlantRequest.getToxicTo_toxic());

        Humidity humidity = new Humidity();
        humidity.setRatio(addPlantRequest.getRatio_humidity());
        humidity.setScale(addPlantRequest.getScale_humidity());

        CommonProblems commonProblems = new CommonProblems();
        commonProblems.setName(addPlantRequest.getName_problems());
        commonProblems.setDetail(addPlantRequest.getDetail_problems());

        CommonPests commonPests = new CommonPests();
        commonPests.setName(addPlantRequest.getName_pests());
        commonPests.setDetail(addPlantRequest.getDetail_pests());

        TemperatureAndSites temperatureAndSites = new TemperatureAndSites();
        temperatureAndSites.setEnd(addPlantRequest.getEnd_temperature());
        temperatureAndSites.setStart(addPlantRequest.getStart_temperature());
        temperatureAndSites.setIndoorMonths(addPlantRequest.getIndoorMonths_temperature());
        temperatureAndSites.setOutDoorMonths(addPlantRequest.getOutDoorMonths_temperature());
        temperatureAndSites.setSentence(addPlantRequest.getSentence_temperature());

        Leaves leaves = new Leaves();
        leaves.setType(addPlantRequest.getType_leaves());
        leaves.setColor(addPlantRequest.getColor_leaves());

        Dimensions dimensions = new Dimensions();
        dimensions.setBeginHeight(addPlantRequest.getBeginHeight_dim());
        dimensions.setEndHeight(addPlantRequest.getEndHeight_dim());
        dimensions.setEndSpread(addPlantRequest.getEndSpread_dim());
        dimensions.setBeginSpread(addPlantRequest.getBeginSpread_dim());

        plantCharacteristic.setClean(addPlantRequest.getClean());
        plantCharacteristic.setLiquid_fertilizing(addPlantRequest.getLiquid_fertilizing());
        plantCharacteristic.setWater(water);
        plantCharacteristic.setLight(light);
        plantCharacteristic.setToxicity(toxicity);
        plantCharacteristic.setHumidity(humidity);
        plantCharacteristic.setCommon_problems(commonProblems);
        plantCharacteristic.setCommon_pests(commonPests);
        plantCharacteristic.setTemperatures(temperatureAndSites);
        plantCharacteristic.setLeaves(leaves);
        plantCharacteristic.setDimensions(dimensions);

        return plantCharacteristic;
    }
}

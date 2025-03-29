package org.agro.agrohack.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.dto.request.IndicateRequest;
import org.agro.agrohack.mapper.IndicateMapper;
import org.agro.agrohack.model.indicators.Indicator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class IndicateMapperImpl implements IndicateMapper {

    @Override
    public Indicator toIndicate(IndicateRequest indicateRequest) {
        Indicator indicator = new Indicator();

        indicator.setTemperatureGround(indicateRequest.getTemperatureGround());
        indicator.setTemperatureAir(indicator.getTemperatureAir());
        indicator.setHeight(indicateRequest.getHeight());
        indicator.setHumidity(indicator.getHumidity());
        indicator.setDate(LocalDateTime.now());

        return indicator;
    }
}

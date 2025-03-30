package org.agro.agrohack.mapper.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agro.agrohack.dto.request.IndicateRequest;
import org.agro.agrohack.mapper.IndicateMapper;
import org.agro.agrohack.model.indicators.Indicator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class IndicateMapperImpl implements IndicateMapper {

    @Override
    public Indicator toIndicate(IndicateRequest indicateRequest) {
        Indicator indicator = new Indicator();

        indicator.setTemperatureGround(indicateRequest.getTemperatureGround());
        indicator.setTemperatureAir(indicateRequest.getTemperatureAir());
        indicator.setHeight(indicateRequest.getHeight());
        indicator.setHumidity(indicateRequest.getHumidity());
        indicator.setDate(LocalDateTime.now());

        return indicator;
    }
}

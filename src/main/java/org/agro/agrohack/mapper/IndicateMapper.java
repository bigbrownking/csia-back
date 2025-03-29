package org.agro.agrohack.mapper;

import org.agro.agrohack.dto.request.IndicateRequest;
import org.agro.agrohack.model.indicators.Indicator;

public interface IndicateMapper {
    Indicator toIndicate(IndicateRequest indicateRequest);
}

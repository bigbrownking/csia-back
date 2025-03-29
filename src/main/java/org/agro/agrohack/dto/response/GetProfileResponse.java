package org.agro.agrohack.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.agro.agrohack.model.UserPlant;

import java.util.List;

@Getter
@Setter
public class GetProfileResponse {
    private String fio;
    private String email;
    private List<UserPlant> plants;
}

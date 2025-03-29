package org.agro.agrohack.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String fio;
    private String role;
}

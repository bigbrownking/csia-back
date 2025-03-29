package org.agro.agrohack.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class EditProfileRequest {
    @Nullable
    private String newPassword;
    @Nullable
    private String fio;

}

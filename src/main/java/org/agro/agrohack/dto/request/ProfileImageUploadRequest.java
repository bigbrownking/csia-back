package org.agro.agrohack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileImageUploadRequest {

    @Schema(description = "Profile image file", type = "string", format = "binary")
    private MultipartFile image;
}

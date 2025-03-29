package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.utils.ParamUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/params")
@Tag(name = "Param Controller",  description = "Endpoints for managing parameters")
public class ParamController {
    private final ParamUtils paramUtils;


    @Operation(summary = "All difficulties of plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All difficulties returned successfully"),
    })
    @GetMapping("/allDifficulties")
    public ResponseEntity<List<String>> getAllDifficulties(){
        return ResponseEntity.ok(paramUtils.getAllDifficulties());
    }


    @Operation(summary = "All substrates for plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All substrates returned successfully"),
    })
    @GetMapping("/allSubstrates")
    public ResponseEntity<List<String>> getAllSubstrates(){
        return ResponseEntity.ok(paramUtils.getAllSubstrates());
    }

}

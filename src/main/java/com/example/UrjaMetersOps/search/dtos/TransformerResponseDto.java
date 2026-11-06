package com.example.UrjaMetersOps.search.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "code",
        "name",
        "feederCode",
        "capacityKva"
})
public class TransformerResponseDto {
    private String code;
    private String name;
    private String feederCode;
    private String capacityKva;
}

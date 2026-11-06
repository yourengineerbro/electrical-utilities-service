package com.example.UrjaMetersOps.search.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "meterId",
        "serialNo",
        "make",
        "phaseType",
        "installStatus",
        "dtCode"
})
public class MeterSearchResponseDto {
    private String meterId;
    private String serialNo;
    private String make;
    private String phaseType;
    private String installStatus;
    private String dtCode;

}

package com.sai.keyReferences.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestDto {
    private String customerId;
    private String vehicleId;
    private List<AdditionalInfo> additionalInfo;
    private UserDetails userInfo;
}

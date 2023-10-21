package com.sai.keyReferences;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sai.keyReferences.dto.AdditionalInfo;
import com.sai.keyReferences.dto.RequestDto;
import com.sai.keyReferences.dto.UserDetails;

import java.util.*;

public class KeyReferencesApplication {

    private static List<String> getSpecialCharacterKeyReferencesDFS(
            Map<String, Object> nestedObject,
            String currentPath) {
        List<String> keyReferences = new ArrayList<>();
        for (Map.Entry<String, Object> entry : nestedObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String fullPath = currentPath.isEmpty() ? key : currentPath + "."
                    + key;
            if (value instanceof String)
                keyReferences.add(String.format("%s=%s", fullPath, value));

            if (value instanceof Map) {
                keyReferences.addAll(getSpecialCharacterKeyReferencesDFS(
                        (Map<String, Object>) value,
                        fullPath));
            }

            if (value instanceof List) {
                for (Map<String, Object> mapValue :
                        (List<Map<String, Object>>) value) {
                    keyReferences.addAll(getSpecialCharacterKeyReferencesDFS(
                            mapValue,
                            fullPath));
                }
            }
        }
        return keyReferences;
    }

    private static RequestDto buildRequestDetails() {
        RequestDto requestDto = new RequestDto();
        requestDto.setCustomerId("12345");
        requestDto.setVehicleId("4567899");
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("a@gmail.com");
        userDetails.setName("sai");
        requestDto.setUserInfo(userDetails);
        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalInfo.setDescription("test");
        additionalInfo.setKeyName("desc");
        requestDto.setAdditionalInfo(Arrays.asList(additionalInfo));
        return requestDto;
    }

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> map = objectMapper
                .convertValue(buildRequestDetails(),
                        new TypeReference<Map<String, Object>>() {
                        });

        System.out.println(getSpecialCharacterKeyReferencesDFS(map, ""));

    }

}



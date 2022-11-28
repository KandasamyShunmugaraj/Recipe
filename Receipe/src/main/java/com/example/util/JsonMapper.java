package com.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.experimental.UtilityClass;

import javax.validation.constraints.NotNull;


@UtilityClass
public class JsonMapper {
    private static final ObjectWriter writer =
            new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writer();

    @NotNull
    public static String toJson(final Object request) {
        try {
            return writer.writeValueAsString(request);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

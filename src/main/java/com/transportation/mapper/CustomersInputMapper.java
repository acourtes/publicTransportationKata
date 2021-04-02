package com.transportation.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transportation.mapper.model.Taps;

import java.io.File;
import java.io.IOException;

public class CustomersInputMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final File inputCustomerJsonFile;

    private CustomersInputMapper(File inputCustomerJsonFile) {
        this.inputCustomerJsonFile = inputCustomerJsonFile;
    }

    public static CustomersInputMapper from(File inputCustomerJsonFile) {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new CustomersInputMapper(inputCustomerJsonFile);
    }

    public Taps getCustomersJourneys() throws IOException {
        return MAPPER.readValue(inputCustomerJsonFile, Taps.class);
    }
}

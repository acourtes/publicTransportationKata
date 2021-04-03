package com.transportation.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transportation.calculator.domain.CustomersSummaries;

import java.io.File;
import java.io.IOException;

public class CustomersSummariesMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final CustomersSummaries customersSummaries;

    private CustomersSummariesMapper(CustomersSummaries customersSummaries) {
        this.customersSummaries = customersSummaries;
    }

    public static CustomersSummariesMapper from(CustomersSummaries customersSummaries) {
        return new CustomersSummariesMapper(customersSummaries);
    }

    public File getJsonFile(File targetOutputFile) {
        try {
            MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValue(targetOutputFile, customersSummaries);
        } catch (IOException e) {
            System.out.println("Error in writing of output file " + targetOutputFile.getName());
        }

        return targetOutputFile;
    }
}

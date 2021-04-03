package com.transportation.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transportation.calculator.domain.CustomerSummariesList;

import java.io.File;
import java.io.IOException;

public class CustomersSummariesMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final CustomerSummariesList customersSummaries;

    private CustomersSummariesMapper(CustomerSummariesList customersSummaries) {
        this.customersSummaries = customersSummaries;
    }

    public static CustomersSummariesMapper from(CustomerSummariesList customersSummaries) {
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

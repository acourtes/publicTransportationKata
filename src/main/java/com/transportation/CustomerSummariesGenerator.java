package com.transportation;

import com.transportation.calculator.JourneyPriceCalculator;
import com.transportation.calculator.UnknownCostException;
import com.transportation.mapper.CustomersInputMapper;
import com.transportation.mapper.CustomersSummariesMapper;

import java.io.File;

public class CustomerSummariesGenerator {

    private final File fileToProcess;

    private CustomerSummariesGenerator(File fileToProcess) {
        this.fileToProcess = fileToProcess;
    }

    public static CustomerSummariesGenerator from(File fileToProcess) {
        return new CustomerSummariesGenerator(fileToProcess);
    }

    public File generateCustomersSummariesFile(File outputFile) throws UnknownCostException {
        var customersJourneys = CustomersInputMapper.from(fileToProcess)
                .getCustomersJourneys();
        var customersSummaries = JourneyPriceCalculator.from(customersJourneys)
                .getCustomersSummaries();
        return CustomersSummariesMapper.from(customersSummaries).getJsonFile(outputFile);
    }
}

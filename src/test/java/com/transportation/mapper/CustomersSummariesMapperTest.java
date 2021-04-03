package com.transportation.mapper;

import com.transportation.TestUtils;
import com.transportation.calculator.domain.CustomersSummaries;
import com.transportation.calculator.domain.CustomersSummary;
import com.transportation.calculator.domain.Trip;
import com.transportation.mapper.domain.Stations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomersSummariesMapperTest {

    private File result;

    @AfterEach
    void deleteGeneratedFile() {
        result.delete();
    }

    @Test
    void should_create_a_file_from_object() {
        var costInCents = 200;
        Trip trip = new Trip(Stations.A, Stations.B, 2, costInCents, 1, 1);
        var customersSummary = new CustomersSummary(1, costInCents, List.of(trip));
        var customersSummaries = new CustomersSummaries(List.of(customersSummary));
        var outputFolder = TestUtils.getFile("outputSamples");
        var filename = "out.json";
        var expectedResult = TestUtils.getFile("outputSamples/expected.txt");

        result = CustomersSummariesMapper.from(customersSummaries).getJsonFile(new File(outputFolder, filename));

        assertThat(result).isNotNull();
        assertThat(result).exists();
        assertThat(result).hasSameTextualContentAs(expectedResult);
    }
}

package com.transportation;

import com.transportation.calculator.UnknownCostException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerSummariesGeneratorTest {

    private File result;

    @AfterEach
    void deletedGeneratedFile() {
        result.delete();
    }

    @Test
    void should_return_customers_summaries_from_inputFile_and_create_an_output_json_file() throws UnknownCostException, IOException {
        var inputFile = TestUtils.getFile("integration/in.json");
        var expectedFile = TestUtils.getFile("integration/expected.json");
        var outputFolder = TestUtils.getFile("integration");
        var filename = "out.json";

        result = CustomerSummariesGenerator.from(inputFile)
                .generateCustomersSummariesFile(new File(outputFolder, filename));

        assertThat(result).exists();
        assertThat(result).hasSameTextualContentAs(expectedFile);
    }
}

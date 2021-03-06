package com.transportation.mapper;

import com.transportation.TestUtils;
import com.transportation.mapper.domain.Stations;
import com.transportation.mapper.domain.Tap;
import com.transportation.mapper.domain.Taps;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomersInputMapperTest {

    @Test
    void should_read_input_file_and_create_a_Taps_object() {
        File inputCustomerFile = TestUtils.getFile("inputSamples/simpleInput.json");

        final Taps result = CustomersInputMapper.from(inputCustomerFile).getCustomersJourneys();

        assertThat(result).isNotNull();
        assertThat(result.tapsList()).hasSize(2);

        var should = new SoftAssertions();
        var firstElement = new Tap(1, 1, Stations.A);
        var secondElement = new Tap(2, 1, Stations.D);
        should.assertThat(result.tapsList().get(0)).isEqualTo(firstElement);
        should.assertThat(result.tapsList().get(1)).isEqualTo(secondElement);
        should.assertAll();
    }

    @Test
    void should_read_input_file_and_create_a_Taps_object_even_when_input_file_has_unknown_fields() {
        File inputCustomerFile = TestUtils.getFile("inputSamples/simpleInputWithUnknownField.json");

        final Taps result = CustomersInputMapper.from(inputCustomerFile).getCustomersJourneys();

        assertThat(result).isNotNull();
        assertThat(result.tapsList()).hasSize(2);
    }

}

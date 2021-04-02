package com.transportation.calculator;

import com.transportation.TestUtils;
import com.transportation.mapper.CustomersInputMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JourneyPriceCalculatorTest {

    @Test
    void should_return_240_for_a_journey_in_zone_1_between_A_and_B_stations() {
        var inputFile = TestUtils.getFile("calculator/zone_1_A_and_B.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        var should = new SoftAssertions();
        var customersSummary = result.customersSummariesList().get(0);
        should.assertThat(customersSummary.customerId()).isEqualTo(1);
        should.assertThat(customersSummary.totalCostInCents()).isEqualTo(expectedPrice);
        should.assertThat(customersSummary.trips()).hasSize(1);

        var trip = customersSummary.trips().get(0);
        should.assertThat(trip.stationStart()).isEqualTo("A");
        should.assertThat(trip.stationEnd()).isEqualTo("B");
        should.assertThat(trip.startedJourneyAt()).isEqualTo(1);
        should.assertThat(trip.costInCents()).isEqualTo(expectedPrice);
        should.assertThat(trip.zoneFrom()).isEqualTo(1);
        should.assertThat(trip.zoneTo()).isEqualTo(1);
        should.assertAll();
    }
}

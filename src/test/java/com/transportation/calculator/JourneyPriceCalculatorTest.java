package com.transportation.calculator;

import com.transportation.TestUtils;
import com.transportation.calculator.domain.CustomersSummaries;
import com.transportation.mapper.CustomersInputMapper;
import com.transportation.mapper.domain.Stations;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JourneyPriceCalculatorTest {
    private SoftAssertions should;

    @BeforeEach
    void setup() {
        should = new SoftAssertions();
    }

    @AfterEach
    void afterTest() {
        should.assertAll();
    }

    @Test
    void should_return_240_for_a_journey_in_zone_1_between_A_and_B_stations() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zone_1_A_and_B_stations.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.A, Stations.B, 1, 1);
    }

    @Test
    void should_return_240_for_a_journey_in_zone_1_between_B_and_A_stations() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zone_1_B_and_A_stations.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.B, Stations.A, 1, 1);
    }

    @Test
    void should_return_240_for_a_journey_in_zones_1_and_2_between_A_and_D_stations() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zones_1_and_2_A_and_D_stations.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.A, Stations.D, 1, 1);
    }

    @Test
    void should_return_240_for_a_journey_in_zones_1_and_2_between_A_and_E_stations() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zones_1_and_2_A_and_E_stations.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.A, Stations.E, 1, 1);
    }

    @Test
    void should_return_240_for_a_journey_in_zones_1_and_2_between_A_and_E_stations_with_customer_id_2() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zones_1_and_2_A_and_E_stations_customer_id_2.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.A, Stations.E, 2, 1);
    }

    @Test
    void should_return_240_for_a_journey_in_zones_1_and_2_between_A_and_E_stations_with_started_journey_at_3() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zones_1_and_2_A_and_E_stations_with_start_time_at_3.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.A, Stations.E, 1, 3);
    }

    @Test
    void should_return_200_for_a_journey_in_zone_3_and_4_between_F_and_G_stations() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zones_3_and_4_F_and_G_stations.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 200;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.F, Stations.G, 1, 1);
    }

    @Test
    void should_return_200_for_a_journey_in_zone_3_and_4_between_G_and_F_stations() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zones_3_and_4_G_and_F_stations.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 200;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.G, Stations.F, 1, 1);
    }

    @Test
    void should_return_280_for_a_journey_from_zone_3_to_zone_2_between_F_and_D_stations() throws UnknownCostException {
        var inputFile = TestUtils.getFile("calculator/zone_3_to_zone_2_F_and_D_stations.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();
        var expectedPrice = 280;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(expectedPrice, result, Stations.F, Stations.D, 1, 1);
    }

    @Test
    void should_return_exception_for_a_journey_with_unknown_cost_rule_between_stations_F_and_UNKNOWN() {
        var inputFile = TestUtils.getFile("calculator/journey_with_unknown_cost_rule.json");
        var customersJourneys = CustomersInputMapper.from(inputFile).getCustomersJourneys();

        assertThatThrownBy(() -> JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries())
                .isInstanceOf(UnknownCostException.class)
                .hasMessage("Unknown cost for stations F and UNKNOWN");
    }

    private void checkResult(int expectedPrice, CustomersSummaries result,
                             Stations stationStart, Stations stationEnd,
                             int expectedCustomerId, int expectedStartedJourney) {
        var customersSummary = result.customersSummariesList().get(0);
        should.assertThat(customersSummary.customerId()).isEqualTo(expectedCustomerId);
        should.assertThat(customersSummary.totalCostInCents()).isEqualTo(expectedPrice);
        should.assertThat(customersSummary.trips()).hasSize(1);

        var trip = customersSummary.trips().get(0);
        should.assertThat(trip.stationStart()).isEqualTo(stationStart);
        should.assertThat(trip.stationEnd()).isEqualTo(stationEnd);
        should.assertThat(trip.startedJourneyAt()).isEqualTo(expectedStartedJourney);
        should.assertThat(trip.costInCents()).isEqualTo(expectedPrice);
        should.assertThat(trip.zoneFrom()).isEqualTo(stationStart.zoneNumber);
        should.assertThat(trip.zoneTo()).isEqualTo(stationEnd.zoneNumber);
    }
}

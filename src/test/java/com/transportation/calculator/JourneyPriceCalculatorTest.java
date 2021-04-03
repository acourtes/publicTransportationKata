package com.transportation.calculator;

import com.transportation.TestUtils;
import com.transportation.calculator.domain.CustomersSummaries;
import com.transportation.calculator.domain.Trip;
import com.transportation.mapper.CustomersInputMapper;
import com.transportation.mapper.domain.Stations;
import com.transportation.mapper.domain.Taps;
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
    void should_return_240_for_a_travel_in_zone_1_between_A_and_B_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_1_A_and_B_stations.json");
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.A, Stations.B, 1, 1);
    }

    @Test
    void should_return_240_for_a_travel_in_zone_1_between_B_and_A_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_1_B_and_A_stations.json");
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.B, Stations.A, 1, 1);
    }

    @Test
    void should_return_240_for_a_travel_in_zones_1_and_2_between_A_and_D_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zones_1_and_2_A_and_D_stations.json");
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.A, Stations.D, 1, 1);
    }

    @Test
    void should_return_240_for_a_travel_in_zones_1_and_2_between_A_and_E_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zones_1_and_2_A_and_E_stations.json");
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.A, Stations.E, 1, 1);
    }

    @Test
    void should_return_240_for_a_travel_in_zones_1_and_2_between_A_and_E_stations_with_customer_id_2() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zones_1_and_2_A_and_E_stations_customer_id_2.json");
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.A, Stations.E, 2, 1);
    }

    @Test
    void should_return_240_for_a_travel_in_zones_1_and_2_between_A_and_E_stations_with_started_travel_at_3() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zones_1_and_2_A_and_E_stations_with_start_time_at_3.json");
        var expectedPrice = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.A, Stations.E, 1, 3);
    }

    @Test
    void should_return_200_for_a_travel_in_zone_3_and_4_between_F_and_G_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zones_3_and_4_F_and_G_stations.json");
        var expectedPrice = 200;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.F, Stations.G, 1, 1);
    }

    @Test
    void should_return_200_for_a_travel_in_zone_3_and_4_between_G_and_F_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zones_3_and_4_G_and_F_stations.json");
        var expectedPrice = 200;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.G, Stations.F, 1, 1);
    }

    @Test
    void should_return_280_for_a_travel_from_zone_3_to_zone_2_between_F_and_D_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_3_to_zone_2_F_and_D_stations.json");
        var expectedPrice = 280;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.F, Stations.D, 1, 1);
    }

    @Test
    void should_return_exception_for_a_travel_with_unknown_cost_rule_between_stations_F_and_UNKNOWN() {
        Taps customersJourneys = getCustomersJourneys("calculator/travel_with_unknown_cost_rule.json");

        assertThatThrownBy(() -> JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries())
                .isInstanceOf(UnknownCostException.class)
                .hasMessage("Unknown cost for stations F and UNKNOWN");
    }

    @Test
    void should_return_280_for_a_travel_from_zone_3_to_zone_1_between_F_and_A_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_3_to_zone_1_F_and_A_stations.json");
        var expectedPrice = 280;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.F, Stations.A, 1, 1);
    }

    @Test
    void should_return_300_for_a_travel_from_zone_4_to_zone_1_between_I_and_A_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_4_to_zone_1_I_and_A_stations.json");
        var expectedPrice = 300;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.I, Stations.A, 1, 1);
    }

    @Test
    void should_return_300_for_a_travel_from_zone_4_to_zone_2_between_I_and_D_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_4_to_zone_2_I_and_D_stations.json");
        var expectedPrice = 300;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.I, Stations.D, 1, 1);
    }

    @Test
    void should_return_280_for_a_travel_from_zone_1_to_zone_3_between_A_and_F_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_1_to_zone_3_A_and_F_stations.json");
        var expectedPrice = 280;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.A, Stations.F, 1, 1);
    }

    @Test
    void should_return_280_for_a_travel_from_zone_2_to_zone_3_between_D_and_F_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_2_to_zone_3_D_and_F_stations.json");
        var expectedPrice = 280;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.D, Stations.F, 1, 1);
    }

    @Test
    void should_return_300_for_a_travel_from_zone_1_to_zone_4_between_A_and_H_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_1_to_zone_4_A_and_H_stations.json");
        var expectedPrice = 300;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.A, Stations.H, 1, 1);
    }

    @Test
    void should_return_300_for_a_travel_from_zone_2_to_zone_4_between_D_and_H_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_2_to_zone_4_D_and_H_stations.json");
        var expectedPrice = 300;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.D, Stations.H, 1, 1);
    }

    @Test
    void should_return_200_for_a_travel_between_E_and_C_stations_which_are_both_in_zones_2_and_3() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zone_3_E_and_C_stations.json");
        var expectedPrice = 200;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);

        checkResult(result, expectedPrice, Stations.E, Stations.C, 1, 1);
    }

    @Test
    void should_return_480_for_a_return_travel_between_A_and_D_stations() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/zones_1_and_2_A_and_D_stations_return_travel.json");
        var expectedTotalPrice = 480;
        var expectedCostPerTravel = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(1);
        should.assertThat(result.customersSummariesList().get(0).totalCostInCents()).isEqualTo(expectedTotalPrice);
        should.assertThat(result.customersSummariesList().get(0).customerId()).isEqualTo(1);

        var trips = result.customersSummariesList().get(0).trips();
        should.assertThat(trips).hasSize(2);
        checkTrip(trips.get(0), expectedCostPerTravel, Stations.A, Stations.D, 1572242400);
        checkTrip(trips.get(1), expectedCostPerTravel, Stations.D, Stations.A, 1572282000);
    }

    @Test
    void should_return_480_for_a_return_travel_between_A_and_D_stations_for_one_customer_and_300_for_a_simple_travel_between_I_to_A_stations_for_another_customer() throws UnknownCostException {
        Taps customersJourneys = getCustomersJourneys("calculator/A_and_D_stations_return_travel_for_one_customer_and_I_and_A_stations_simple_travel_for_another_customer.json");
        var expectedTotalPriceForCustomer1 = 480;
        var expectedTotalPriceForCustomer2 = 300;
        var expectedCostPerTravel = 240;

        CustomersSummaries result = JourneyPriceCalculator.from(customersJourneys).getCustomersSummaries();

        assertThat(result).isNotNull();
        assertThat(result.customersSummariesList()).hasSize(2);
        should.assertThat(result.customersSummariesList().get(0).totalCostInCents()).isEqualTo(expectedTotalPriceForCustomer1);
        should.assertThat(result.customersSummariesList().get(0).customerId()).isEqualTo(1);
        should.assertThat(result.customersSummariesList().get(1).totalCostInCents()).isEqualTo(expectedTotalPriceForCustomer2);
        should.assertThat(result.customersSummariesList().get(1).customerId()).isEqualTo(2);

        var tripsForCustomer1 = result.customersSummariesList().get(0).trips();
        should.assertThat(tripsForCustomer1).hasSize(2);
        checkTrip(tripsForCustomer1.get(0), expectedCostPerTravel, Stations.A, Stations.D, 1572242400);
        checkTrip(tripsForCustomer1.get(1), expectedCostPerTravel, Stations.D, Stations.A, 1572282000);

        var tripsForCustomer2 = result.customersSummariesList().get(1).trips();
        should.assertThat(tripsForCustomer2).hasSize(1);
        checkTrip(tripsForCustomer2.get(0), expectedTotalPriceForCustomer2, Stations.I, Stations.A, 2);
    }

    private Taps getCustomersJourneys(String testFile) {
        var inputFile = TestUtils.getFile(testFile);
        return CustomersInputMapper.from(inputFile).getCustomersJourneys();
    }

    private void checkResult(CustomersSummaries result, int expectedPrice,
                             Stations stationStart, Stations stationEnd,
                             int expectedCustomerId, int expectedStartedJourney) {
        var customersSummary = result.customersSummariesList().get(0);
        should.assertThat(customersSummary.customerId()).isEqualTo(expectedCustomerId);
        should.assertThat(customersSummary.totalCostInCents()).isEqualTo(expectedPrice);
        should.assertThat(customersSummary.trips()).hasSize(1);

        var trip = customersSummary.trips().get(0);
        checkTrip(trip, expectedPrice, stationStart, stationEnd, expectedStartedJourney);
    }

    private void checkTrip(Trip trip, int expectedPrice, Stations stationStart, Stations stationEnd,
                           int expectedStartedJourney) {
        should.assertThat(trip.stationStart()).isEqualTo(stationStart);
        should.assertThat(trip.stationEnd()).isEqualTo(stationEnd);
        should.assertThat(trip.startedJourneyAt()).isEqualTo(expectedStartedJourney);
        should.assertThat(trip.costInCents()).isEqualTo(expectedPrice);
        should.assertThat(trip.zoneFrom()).isIn(stationStart.zoneNumbers);
        should.assertThat(trip.zoneTo()).isIn(stationEnd.zoneNumbers);
    }
}

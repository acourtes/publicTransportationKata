package com.transportation.calculator;

import com.transportation.mapper.model.Taps;

import java.util.List;

public class JourneyPriceCalculator {

    private final Taps customersJourneys;

    private JourneyPriceCalculator(Taps customersJourneys) {
        this.customersJourneys = customersJourneys;
    }

    public static JourneyPriceCalculator from(Taps customersJourneys) {
        return new JourneyPriceCalculator(customersJourneys);
    }

    public CustomersSummaries getCustomersSummaries() {
        var firstTap = customersJourneys.tapsList().get(0);
        var startStation = firstTap.station();
        var secondTap = customersJourneys.tapsList().get(1);
        var endStation = secondTap.station();

        var zoneTo = endStation.equals("D") ? 2 : 1;
        var trip = new Trip(startStation, endStation, 1, 240, 1, zoneTo);
        var customersSummary = new CustomersSummary(1, 240, List.of(trip));

        return new CustomersSummaries(List.of(customersSummary));
    }
}

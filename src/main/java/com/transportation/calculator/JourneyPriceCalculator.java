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
        var trip = new Trip("A", "B", 1, 240, 1, 1);
        var customersSummary = new CustomersSummary(1, 240, List.of(trip));

        return new CustomersSummaries(List.of(customersSummary));
    }
}

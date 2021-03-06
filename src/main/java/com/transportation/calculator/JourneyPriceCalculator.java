package com.transportation.calculator;

import com.transportation.calculator.domain.CustomerSummariesList;
import com.transportation.calculator.domain.CustomerSummary;
import com.transportation.calculator.domain.Trip;
import com.transportation.mapper.domain.Tap;
import com.transportation.mapper.domain.Taps;
import com.transportation.utils.TapsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JourneyPriceCalculator {

    private final Taps customersJourneys;

    private JourneyPriceCalculator(Taps customersJourneys) {
        this.customersJourneys = customersJourneys;
    }

    public static JourneyPriceCalculator from(Taps customersJourneys) {
        return new JourneyPriceCalculator(customersJourneys);
    }

    public CustomerSummariesList getCustomersSummaries() throws UnknownCostException {
        var tapsByCustomerId = TapsUtils.getTapsByCustomerId(customersJourneys.tapsList());
        final List<CustomerSummary> customersSummaries = new ArrayList<>(tapsByCustomerId.size());

        for (Map.Entry<Integer, List<Tap>> entry : tapsByCustomerId.entrySet()) {
            int customerId = entry.getKey();
            List<Trip> trips = TapsUtils.getTripsFromTaps(entry.getValue());
            var totalCost = getTotalCost(trips);
            var customersSummary = new CustomerSummary(customerId, totalCost, trips);
            customersSummaries.add(customersSummary);
        }

        return new CustomerSummariesList(customersSummaries);
    }

    private Integer getTotalCost(List<Trip> trips) {
        return trips.stream()
                .map(Trip::costInCents)
                .reduce(0, Integer::sum);
    }

}

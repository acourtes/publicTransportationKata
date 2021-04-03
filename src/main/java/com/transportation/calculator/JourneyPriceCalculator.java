package com.transportation.calculator;

import com.transportation.calculator.domain.CustomersSummaries;
import com.transportation.calculator.domain.CustomersSummary;
import com.transportation.calculator.domain.Trip;
import com.transportation.calculator.rules.CostRule;
import com.transportation.calculator.rules.CostRuleManager;
import com.transportation.mapper.domain.Stations;
import com.transportation.mapper.domain.Taps;

import java.util.List;

public class JourneyPriceCalculator {

    private final Taps customersJourneys;

    private JourneyPriceCalculator(Taps customersJourneys) {
        this.customersJourneys = customersJourneys;
    }

    public static JourneyPriceCalculator from(Taps customersJourneys) {
        return new JourneyPriceCalculator(customersJourneys);
    }

    public CustomersSummaries getCustomersSummaries() throws UnknownCostException {
        var firstTap = customersJourneys.tapsList().get(0);
        var startStation = firstTap.station();
        var customerId = firstTap.customerId();
        var startedJourneyAt = firstTap.unixTimestamp();
        var secondTap = customersJourneys.tapsList().get(1);
        var endStation = secondTap.station();

        int cost = getCost(startStation, endStation);

        var trip = new Trip(startStation, endStation, startedJourneyAt, cost,
                startStation.zoneNumber, endStation.zoneNumber);
        var customersSummary = new CustomersSummary(customerId, cost, List.of(trip));

        return new CustomersSummaries(List.of(customersSummary));
    }

    private int getCost(Stations startStation, Stations endStation) throws UnknownCostException {
        var costRules = CostRuleManager.getCostRules();

        return costRules.stream()
                .filter(costRule -> costRule.stationsRule().test(startStation, endStation))
                .map(CostRule::cost)
                .findFirst()
                .orElseThrow(()
                        -> new UnknownCostException(
                                "Unknown cost for stations %s and %s".formatted(startStation, endStation)));
    }
}

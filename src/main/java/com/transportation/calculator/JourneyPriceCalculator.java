package com.transportation.calculator;

import com.transportation.calculator.domain.CustomersSummaries;
import com.transportation.calculator.domain.CustomersSummary;
import com.transportation.calculator.domain.Trip;
import com.transportation.calculator.rules.CostRule;
import com.transportation.calculator.rules.CostRuleManager;
import com.transportation.mapper.domain.Stations;
import com.transportation.mapper.domain.Taps;

import java.util.Comparator;
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
        var customerId = firstTap.customerId();
        Trip trip = getTrip(firstTap);
        var customersSummary = new CustomersSummary(customerId, trip.costInCents(), List.of(trip));

        return new CustomersSummaries(List.of(customersSummary));
    }

    private Trip getTrip(com.transportation.mapper.domain.Tap firstTap) throws UnknownCostException {
        var startStation = firstTap.station();
        var startedJourneyAt = firstTap.unixTimestamp();
        var secondTap = customersJourneys.tapsList().get(1);
        var endStation = secondTap.station();

        var bestCostRule = getCostRules(startStation, endStation);
        int cost = bestCostRule.cost();
        var startZone = getStartZone(bestCostRule, startStation);
        var endZone = getEndZone(bestCostRule, endStation);

        return new Trip(startStation, endStation, startedJourneyAt, cost,
                startZone, endZone);
    }

    private int getRightZone(List<Integer> zonesFromCostRule, List<Integer> zonesFromStation) {
        if (zonesFromCostRule.size() == 1) {
            return zonesFromCostRule.get(0);
        }

        return zonesFromCostRule.stream()
                .filter(zonesFromStation::contains)
                .findFirst()
                .orElse(0);
    }

    private int getEndZone(CostRule costRule, Stations endStation) {
        return getRightZone(costRule.endZones(), endStation.zoneNumbers);
    }

    private int getStartZone(CostRule costRule, Stations startStation) {
        return getRightZone(costRule.startZones(), startStation.zoneNumbers);
    }



    private CostRule getCostRules(Stations startStation, Stations endStation) throws UnknownCostException {
        var costRules = CostRuleManager.getCostRules();

        return costRules.stream()
                .filter(costRule -> costRule.stationsRule().test(startStation, endStation))
                .min(Comparator.comparing(CostRule::cost))
                .orElseThrow(() -> new UnknownCostException(
                        "Unknown cost for stations %s and %s".formatted(startStation, endStation)));
    }
}

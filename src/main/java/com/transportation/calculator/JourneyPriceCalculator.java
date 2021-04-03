package com.transportation.calculator;

import com.transportation.calculator.domain.CustomersSummaries;
import com.transportation.calculator.domain.CustomersSummary;
import com.transportation.calculator.domain.Trip;
import com.transportation.calculator.rules.CostRule;
import com.transportation.calculator.rules.CostRuleManager;
import com.transportation.mapper.domain.Stations;
import com.transportation.mapper.domain.Taps;

import java.util.ArrayList;
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
        int customerId = getCustomerId();
        var trips = getTrips();
        var totalCost = getTotalCost(trips);
        var customersSummary = new CustomersSummary(customerId, totalCost, trips);

        return new CustomersSummaries(List.of(customersSummary));
    }

    private Integer getTotalCost(List<Trip> trips) {
        return trips.stream()
                .map(Trip::costInCents)
                .reduce(0, Integer::sum);
    }

    private int getCustomerId() {
        return customersJourneys.tapsList().get(0).customerId();
    }

    private List<Trip> getTrips() throws UnknownCostException {
        List<Trip> trips = new ArrayList<>(customersJourneys.tapsList().size() / 2);
        for (int i = 0; i < customersJourneys.tapsList().size(); i += 2) {
            var firstTap = customersJourneys.tapsList().get(i);
            var startStation = firstTap.station();
            var startedJourneyAt = firstTap.unixTimestamp();
            var secondTap = customersJourneys.tapsList().get(i + 1);
            var endStation = secondTap.station();

            var bestCostRule = getCostRules(startStation, endStation);
            int cost = bestCostRule.cost();
            var startZone = getStartZone(bestCostRule, startStation);
            var endZone = getEndZone(bestCostRule, endStation);

            trips.add(new Trip(startStation, endStation, startedJourneyAt, cost,
                    startZone, endZone));
        }

        return trips;
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

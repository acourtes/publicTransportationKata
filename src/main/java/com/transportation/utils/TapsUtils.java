package com.transportation.utils;

import com.transportation.calculator.UnknownCostException;
import com.transportation.calculator.domain.Trip;
import com.transportation.calculator.rules.CostRule;
import com.transportation.calculator.rules.CostRuleManager;
import com.transportation.mapper.domain.Stations;
import com.transportation.mapper.domain.Tap;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TapsUtils {

    private static final int NUMBER_OF_TAPS_BY_TRIP = 2;

    private TapsUtils() {
    }

    public static TreeMap<Integer, List<Tap>> getTapsByCustomerId(List<Tap> taps) {
        return taps.stream()
                .map(Tap::customerId)
                .collect(Collectors.toMap(Function.identity(),
                        id -> getTapsListById(id, taps),
                        noMerge(),
                        TreeMap::new));
    }

    private static BinaryOperator<List<Tap>> noMerge() {
        return (taps, taps2) -> taps;
    }

    private static List<Tap> getTapsListById(Integer id, List<Tap> taps) {
        return taps.stream()
                .filter(t -> t.customerId() == id)
                .toList();
    }

    public static List<Trip> getTripsFromTaps(List<Tap> taps) throws UnknownCostException {
        List<Trip> trips = new ArrayList<>(taps.size() / NUMBER_OF_TAPS_BY_TRIP);

        for (int i = 0; i < taps.size(); i += NUMBER_OF_TAPS_BY_TRIP) {
            var firstTap = taps.get(i);
            var startStation = firstTap.station();
            var startedJourneyAt = firstTap.unixTimestamp();
            var secondTap = taps.get(i + 1);
            var endStation = secondTap.station();

            var bestCostRule = CostRuleManager.getBestCostRuleFor(startStation, endStation);
            int cost = bestCostRule.cost();
            var startZone = getStartZone(bestCostRule, startStation);
            var endZone = getEndZone(bestCostRule, endStation);

            trips.add(new Trip(startStation, endStation, startedJourneyAt, cost,
                    startZone, endZone));
        }

        return trips;
    }

    private static int getRightZone(List<Integer> zonesFromCostRule, List<Integer> zonesFromStation) {
        if (zonesFromCostRule.size() == 1) {
            return zonesFromCostRule.get(0);
        }

        return zonesFromCostRule.stream()
                .filter(zonesFromStation::contains)
                .findFirst()
                .orElse(0);
    }

    private static int getEndZone(CostRule costRule, Stations endStation) {
        return getRightZone(costRule.endZones(), endStation.zoneNumbers);
    }

    private static int getStartZone(CostRule costRule, Stations startStation) {
        return getRightZone(costRule.startZones(), startStation.zoneNumbers);
    }
}

package com.transportation.calculator.rules;

import com.transportation.calculator.UnknownCostException;
import com.transportation.mapper.domain.Stations;

import java.util.Comparator;
import java.util.List;

import static com.transportation.TransportationConstants.ZONE_1;
import static com.transportation.TransportationConstants.ZONE_2;
import static com.transportation.TransportationConstants.ZONE_3;
import static com.transportation.TransportationConstants.ZONE_4;

public final class CostRuleManager {

    private static final int COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_1_AND_2 = 240;
    private static final int COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_3_AND_4 = 200;
    private static final int COST_IN_CENTS_FROM_ZONE_3_TO_ZONES_1_OR_2 = 280;
    private static final int COST_IN_CENTS_FROM_ZONE_4_TO_ZONES_1_OR_2 = 300;
    private static final int COST_IN_CENTS_FROM_ZONE_1_OR_2_TO_ZONE_3 = 280;
    private static final int COST_IN_CENTS_FROM_ZONE_1_OR_2_TO_ZONE_4 = 300;
    public static final List<Integer> ZONE_THREE = List.of(ZONE_3);
    public static final List<Integer> ZONE_FOUR = List.of(ZONE_4);
    public static final List<Integer> ZONES_ONE_AND_TWO = List.of(ZONE_1, ZONE_2);
    public static final List<Integer> ZONES_THREE_AND_FOUR = List.of(ZONE_3, ZONE_4);

    private CostRuleManager(){}

    public static CostRule getBestCostRuleFor(Stations startStation, Stations endStation)
            throws UnknownCostException {
        var costRules = getCostRules();

        return costRules.stream()
                .filter(costRule -> costRule.stationsRule().test(startStation, endStation))
                .min(Comparator.comparing(CostRule::cost))
                .orElseThrow(() -> new UnknownCostException(
                        "Unknown cost for stations %s and %s".formatted(startStation, endStation)));
    }

    private static List<CostRule> getCostRules() {
        return List.of(
                new CostRule(stationsWithInZoneOneAndTwo(),
                        COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_1_AND_2, ZONES_ONE_AND_TWO, ZONES_ONE_AND_TWO),
                new CostRule(stationsWithInZoneThreeAndFour(),
                        COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_3_AND_4, ZONES_THREE_AND_FOUR, ZONES_THREE_AND_FOUR),
                new CostRule(startStationInZoneThreeToStationInZoneOneOrTwo(),
                        COST_IN_CENTS_FROM_ZONE_3_TO_ZONES_1_OR_2, ZONE_THREE, ZONES_ONE_AND_TWO),
                new CostRule(startStationInZoneFourToStationInZoneOneOrTwo(),
                        COST_IN_CENTS_FROM_ZONE_4_TO_ZONES_1_OR_2, ZONE_FOUR, ZONES_ONE_AND_TWO),
                new CostRule(startStationInZoneOneOrTwoToStationInZoneThree(),
                        COST_IN_CENTS_FROM_ZONE_1_OR_2_TO_ZONE_3, ZONES_ONE_AND_TWO, ZONE_THREE),
                new CostRule(startStationInZoneOneOrTwoToStationInZoneFour(),
                        COST_IN_CENTS_FROM_ZONE_1_OR_2_TO_ZONE_4, ZONES_ONE_AND_TWO, ZONE_FOUR));
    }

    private static StationsRule stationsWithInZoneOneAndTwo() {
        return (startStation, endStation) ->
                isStationInZones(startStation, ZONE_1, ZONE_2)
                        && isStationInZones(endStation, ZONE_1, ZONE_2);
    }

    private static StationsRule stationsWithInZoneThreeAndFour() {
        return (startStation, endStation) ->
                isStationInZones(startStation, ZONE_3, ZONE_4)
                        && isStationInZones(endStation, ZONE_3, ZONE_4);
    }

    private static StationsRule startStationInZoneThreeToStationInZoneOneOrTwo() {
        return (startStation, endStation) ->
                startStation.isInZone(ZONE_3)
                        && isStationInZones(endStation, ZONE_1, ZONE_2);
    }

    private static StationsRule startStationInZoneFourToStationInZoneOneOrTwo() {
        return (startStation, endStation) ->
                startStation.isInZone(ZONE_4)
                        && isStationInZones(endStation, ZONE_1, ZONE_2);
    }

    private static StationsRule startStationInZoneOneOrTwoToStationInZoneThree() {
        return (startStation, endStation) ->
                isStationInZones(startStation, ZONE_1, ZONE_2)
                        && endStation.isInZone(ZONE_3);
    }

    private static StationsRule startStationInZoneOneOrTwoToStationInZoneFour() {
        return (startStation, endStation) ->
                isStationInZones(startStation, ZONE_1, ZONE_2)
                        && endStation.isInZone(ZONE_4);
    }

    private static boolean isStationInZones(Stations station, int firstZone, int secondZone) {
        return station.isInZone(firstZone) || station.isInZone(secondZone);
    }
}

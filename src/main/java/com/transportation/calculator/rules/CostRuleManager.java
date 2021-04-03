package com.transportation.calculator.rules;

import com.transportation.mapper.domain.Stations;

import java.util.List;

import static com.transportation.TransportationConstants.*;

public final class CostRuleManager {

    private static final int COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_1_AND_2 = 240;
    private static final int COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_3_AND_4 = 200;
    private static final int COST_IN_CENTS_FROM_ZONE_3_TO_ZONES_1_OR_2 = 280;
    private static final int COST_IN_CENTS_FROM_ZONE_4_TO_ZONES_1_OR_2 = 300;
    private static final int COST_IN_CENTS_FROM_ZONE_1_OR_2_TO_ZONE_3 = 280;
    private static final int COST_IN_CENTS_FROM_ZONE_1_OR_2_TO_ZONE_4 = 300;

    public static List<CostRule> getCostRules() {
        return List.of(
                new CostRule(stationsWithInZoneOneAndTwo(), COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_1_AND_2),
                new CostRule(stationsWithInZoneThreeAndFour(), COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_3_AND_4),
                new CostRule(startStationInZoneThreeToStationInZoneOneOrTwo(), COST_IN_CENTS_FROM_ZONE_3_TO_ZONES_1_OR_2),
                new CostRule(startStationInZoneFourToStationInZoneOneOrTwo(), COST_IN_CENTS_FROM_ZONE_4_TO_ZONES_1_OR_2),
                new CostRule(startStationInZoneOneOrTwoToStationInZoneThree(), COST_IN_CENTS_FROM_ZONE_1_OR_2_TO_ZONE_3),
                new CostRule(startStationInZoneOneOrTwoToStationInZoneFour(), COST_IN_CENTS_FROM_ZONE_1_OR_2_TO_ZONE_4));
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

package com.transportation.calculator;

import com.transportation.mapper.model.Stations;
import com.transportation.mapper.model.Taps;

import java.util.List;

import static com.transportation.TransportationConstants.ZONE_1;
import static com.transportation.TransportationConstants.ZONE_2;
import static com.transportation.TransportationConstants.ZONE_3;
import static com.transportation.TransportationConstants.ZONE_4;

public class JourneyPriceCalculator {

    public static final int COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_1_AND_2 = 240;
    public static final int COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_3_AND_4 = 200;
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

    private int getCost(Stations startStation, Stations endStation) {
        int cost = 0;
        var startStationIsOneOrTwo = isStationInZones(startStation, ZONE_1, ZONE_2);
        var endStationIsOneOrTwo = isStationInZones(endStation, ZONE_1, ZONE_2);
        var startStationIsThreeOrFour = isStationInZones(startStation, ZONE_3, ZONE_4);
        var endStationIsThreeOrFour = isStationInZones(endStation, ZONE_3, ZONE_4);

        if (startStationIsOneOrTwo && endStationIsOneOrTwo) {
            cost = COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_1_AND_2;
        } else if (startStationIsThreeOrFour && endStationIsThreeOrFour) {
            cost = COST_IN_CENTS_FOR_JOURNEY_WITHIN_ZONES_3_AND_4;
        }
        return cost;
    }

    private boolean isStationInZones(Stations startStation, int firstZone, int secondZone) {
        return startStation.zoneNumber == firstZone || startStation.zoneNumber == secondZone;
    }
}

package com.transportation.utils;

import com.transportation.calculator.UnknownCostException;
import com.transportation.mapper.domain.Stations;
import com.transportation.mapper.domain.Tap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TapsUtilsTest {

    @Test
    void should_create_ordered_map_with_id_and_associatedTaps() {
        var tap = new Tap(0, 0, Stations.A);
        var tap2 = new Tap(0, 1, Stations.A);
        var tap3 = new Tap(0, 2, Stations.A);
        var taps = List.of(tap2, tap3, tap);

        var result = TapsUtils.getTapsByCustomerId(taps);

        assertThat(result).hasSize(3);
        for (int i = 0; i < result.keySet().size(); i++) {
            assertThat(result.get(i)).hasSize(1);
            assertThat(result.get(i).get(0).customerId()).isEqualTo(i);
        }
    }

    @Test
    void should_return_trips_from_taps() throws UnknownCostException {
        var startStation = Stations.A;
        var customerId = 1;
        var tap = new Tap(2, customerId, startStation);
        var endStation = Stations.C;
        var tap2 = new Tap(4, customerId, endStation);
        var taps = List.of(tap, tap2);

        var result = TapsUtils.getTripsFromTaps(taps);

        assertThat(result).hasSize(1);
        var trip = result.get(0);
        assertThat(trip.startedJourneyAt()).isEqualTo(2);
        assertThat(trip.zoneFrom()).isIn(startStation.zoneNumbers);
        assertThat(trip.zoneTo()).isIn(endStation.zoneNumbers);
        assertThat(trip.stationStart()).isEqualTo(startStation);
        assertThat(trip.stationEnd()).isEqualTo(endStation);
    }
}
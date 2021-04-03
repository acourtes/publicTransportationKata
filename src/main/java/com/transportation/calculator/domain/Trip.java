package com.transportation.calculator.domain;

import com.transportation.mapper.domain.Stations;

public record Trip(Stations stationStart, Stations stationEnd, int startedJourneyAt,
                   int costInCents, int zoneFrom, int zoneTo) {
}

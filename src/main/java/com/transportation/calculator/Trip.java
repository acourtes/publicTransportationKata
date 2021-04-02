package com.transportation.calculator;

import com.transportation.mapper.model.Stations;

public record Trip(Stations stationStart, Stations stationEnd, int startedJourneyAt,
                   int costInCents, int zoneFrom, int zoneTo) {
}

package com.transportation.calculator;

public record Trip(String stationStart, String stationEnd, int startedJourneyAt,
                   int costInCents, int zoneFrom, int zoneTo) {
}

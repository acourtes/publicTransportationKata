package com.transportation.calculator.rules;

import java.util.List;

public record CostRule(StationsRule stationsRule, Integer cost,
                       List<Integer> startZones, List<Integer> endZones) {
}

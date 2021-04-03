package com.transportation.calculator.domain;

import java.util.List;

public record CustomersSummary(int customerId, int totalCostInCents,
                               List<Trip> trips) {
}

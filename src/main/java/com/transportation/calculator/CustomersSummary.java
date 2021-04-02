package com.transportation.calculator;

import java.util.List;

public record CustomersSummary(int customerId, int totalCostInCents,
                               List<Trip> trips) {
}

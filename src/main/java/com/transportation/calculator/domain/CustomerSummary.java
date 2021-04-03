package com.transportation.calculator.domain;

import java.util.List;

public record CustomerSummary(int customerId, int totalCostInCents,
                              List<Trip> trips) {
}

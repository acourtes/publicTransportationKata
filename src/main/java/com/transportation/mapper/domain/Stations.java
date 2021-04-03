package com.transportation.mapper.domain;

import java.util.Arrays;
import java.util.List;

public enum Stations {
    A(1), B(1), C(2, 3),
    D(2), E(2, 3), F(3, 4),
    G(4), H(4), I(4),
    UNKNOWN(8);

    public final List<Integer> zoneNumbers;

    Stations(Integer... zoneNumbers) {
        this.zoneNumbers = Arrays.asList(zoneNumbers);
    }

    public boolean isInZone(Integer zoneNumber) {
        return zoneNumbers.contains(zoneNumber);
    }
}

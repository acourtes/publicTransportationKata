package com.transportation.mapper.domain;

public enum Stations {
    A(1), B(1), D(2), E(2), F(3), G(4),
    UNKNOWN(8);

    public final int zoneNumber;

    Stations(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public boolean isInZone(int zoneNumber) {
        return this.zoneNumber == zoneNumber;
    }
}

package com.transportation.mapper.model;

public enum Stations {
    A(1), B(1), D(2), E(2), F(3), G(4);

    public final int zoneNumber;

    Stations(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }
}

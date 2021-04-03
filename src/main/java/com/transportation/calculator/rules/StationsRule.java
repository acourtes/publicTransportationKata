package com.transportation.calculator.rules;

import com.transportation.mapper.domain.Stations;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface StationsRule extends BiPredicate<Stations, Stations> {

}

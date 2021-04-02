package com.transportation.mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Taps (@JsonProperty("taps") List<Tap> tapsList) {
}

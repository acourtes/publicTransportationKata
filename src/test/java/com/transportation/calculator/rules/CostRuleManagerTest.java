package com.transportation.calculator.rules;

import com.transportation.calculator.UnknownCostException;
import com.transportation.mapper.domain.Stations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CostRuleManagerTest {

    @Test
    void should_select_best_cost_for_travel_between_stations_E_and_C() throws UnknownCostException {
        var result = CostRuleManager.getBestCostRuleFor(Stations.E, Stations.C);

        assertThat(result.cost()).isEqualTo(200);
    }
}
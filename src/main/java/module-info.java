module publicTransportationKataModule {
    requires com.fasterxml.jackson.databind;
    exports com.transportation.mapper.domain;
    opens com.transportation.mapper.domain;
    opens com.transportation.mapper;
    opens com.transportation.calculator;
    opens com.transportation.calculator.domain;
    opens com.transportation.calculator.rules;
    opens com.transportation.utils;
    opens com.transportation;
}
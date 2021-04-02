module publicTransportationKataModule {
    requires com.fasterxml.jackson.databind;
    exports com.transportation.mapper.model;
    opens com.transportation.mapper.model;
    opens com.transportation.mapper;
    opens com.transportation.calculator;
}
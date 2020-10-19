package com.epam.gtc.services.factory;

import com.epam.gtc.services.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ServiceFactoryTest {

    @DisplayName("ServiceFactory test")
    @ParameterizedTest(name = ("{index} When type is: {0}, service is: {1}"))
    @MethodSource({"getService"})
    public void check(final ServiceType type, final Class<? extends BaseService> clazz) {
        assertTrue(clazz.isInstance(ServiceFactory.createService(type)));
    }

    private static Stream<Arguments> getService() {
        return Stream.of(
                arguments(ServiceType.CITY_SERVICE, CityService.class),
                arguments(ServiceType.DELIVERY_SERVICE, DeliveryService.class),
                arguments(ServiceType.DISTANCE_SERVICE, DistanceService.class),
                arguments(ServiceType.INVOICE_SERVICE, InvoiceService.class),
                arguments(ServiceType.RATE_SERVICE, RateService.class),
                arguments(ServiceType.REQUEST_SERVICE, RequestService.class),
                arguments(ServiceType.USER_SERVICE, UserService.class)
                );
    }
}
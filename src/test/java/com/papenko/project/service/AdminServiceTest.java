package com.papenko.project.service;

import com.papenko.project.entity.*;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
import com.papenko.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    BusRepository busRepository;
    @Mock
    RouteRepository routeRepository;
    @Mock
    DriverRepository driverRepository;
    @InjectMocks
    AdminService adminService;

    @Test
    void getDriversEmails_shouldReturnListWithEmailsOfEveryDriver() {
        // GIVEN
        User driverUser0 = new User("alexa@company.com", UserType.BUS_DRIVER, "$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG");
        User driverUser1 = new User("bob.jenkins@gmail.com", UserType.BUS_DRIVER, "$2a$10$rRsTiuqd3V5hQJwsLi3CneRCcKxK0eiKKO1JlGIxAnx9NIP4GsHbG");
        given(userRepository.findAllDrivers()).willReturn(List.of(driverUser0, driverUser1));
        // WHEN
        List<String> driversEmail = adminService.getDriversEmails();
        // THEN
        assertEquals(List.of("alexa@company.com", "bob.jenkins@gmail.com"), driversEmail);
    }

    @Test
    void getBusesSerials_shouldReturnArrayWithSerialsOfEveryBus() {
        // GIVEN
        Bus bus0 = new Bus("AA4444AA", "69");
        Bus bus1 = new Bus("II1111II", "6k");
        given(busRepository.findAllBuses()).willReturn(List.of(bus0, bus1));
        // WHEN
        List<String> busesSerials = adminService.getBusesSerials();
        // THEN
        assertEquals(List.of("AA4444AA", "II1111II"), busesSerials);
    }

    @Test
    void getRoutesNames_shouldReturnArrayWithNamesOfEveryRoute() {
        // GIVEN
        given(routeRepository.findAllRoutes()).willReturn(List.of(new Route("7L"), new Route("7k")));
        // WHEN
        List<String> routesNames = adminService.getRoutesNames();
        // THEN
        assertEquals(List.of("7L", "7k"), routesNames);
    }

    @Test
    void assignDriverToBus_shouldInitiateAssigningDriverToBus() {
        // GIVEN
        // WHEN
        adminService.assignDriverToBus("bob.jenkins@gmail.com", "II1111II");
        // THEN
        verify(driverRepository).updateDriver(new Driver("bob.jenkins@gmail.com", "II1111II"));
    }

    @Test
    void vacateDriverFromBus_shouldInitiateVacatingDriverFromBus() {
        // GIVEN
        // WHEN
        adminService.vacateDriverFromBus("bob.jenkins@gmail.com");
        // THEN
        verify(driverRepository).updateDriver(new Driver("bob.jenkins@gmail.com", null));
    }

    @Test
    void assignBusToRoute_shouldInitiateAssigningBusToRoute() {
        // GIVEN
        // WHEN
        adminService.assignBusToRoute("AA0099AA", "96");
        // THEN
        verify(busRepository).updateBus(new Bus("AA0099AA", "96"));
    }
}
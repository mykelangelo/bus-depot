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
    void getDrivers_shouldReturnListWithEveryDriver() {
        // GIVEN
        given(driverRepository.findAllDrivers()).willReturn(List.of(new Driver("alexa@company.com", "BB8698BB"), new Driver("bob.jenkins@gmail.com", "AA4444AA")));
        // WHEN
        List<Driver> driversEmail = adminService.getDrivers();
        // THEN
        assertEquals(List.of(new Driver("alexa@company.com", "BB8698BB"), new Driver("bob.jenkins@gmail.com", "AA4444AA")), driversEmail);
    }

    @Test
    void getBuses_shouldReturnListEveryBus() {
        // GIVEN
        given(busRepository.findAllBuses()).willReturn(List.of(new Bus("AA4444AA", "69"), new Bus("II1111II", "6k")));
        // WHEN
        List<Bus> busesSerials = adminService.getBuses();
        // THEN
        assertEquals(List.of(new Bus("AA4444AA", "69"), new Bus("II1111II", "6k")), busesSerials);
    }

    @Test
    void getRoutes_shouldReturnListWithEveryRoute() {
        // GIVEN
        given(routeRepository.findAllRoutes()).willReturn(List.of(new Route("7L"), new Route("7k")));
        // WHEN
        List<Route> routesNames = adminService.getRoutes();
        // THEN
        assertEquals(List.of(new Route("7L"), new Route("7k")), routesNames);
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
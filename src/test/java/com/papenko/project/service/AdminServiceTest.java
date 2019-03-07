package com.papenko.project.service;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
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
        given(driverRepository.findAllDrivers()).willReturn(List.of(
                new Driver("alexa@company.com", new Bus("BB8698BB", new Route("R9")), true),
                new Driver("bob.jenkins@gmail.com", new Bus("AA4444AA", new Route("K9")), true)));
        // WHEN
        List<Driver> driversEmail = adminService.getDrivers();
        // THEN
        assertEquals(List.of(
                new Driver("alexa@company.com", new Bus("BB8698BB", new Route("R9")), true),
                new Driver("bob.jenkins@gmail.com", new Bus("AA4444AA", new Route("K9")), true)),
                driversEmail);
    }

    @Test
    void getBuses_shouldReturnListWithEveryBus() {
        // GIVEN
        given(busRepository.findAllBuses()).willReturn(List.of(new Bus("AA4444AA", new Route("69")),
                new Bus("II1111II", new Route("6k"))));
        // WHEN
        List<Bus> busesSerials = adminService.getBuses();
        // THEN
        assertEquals(List.of(
                new Bus("AA4444AA", new Route("69")),
                new Bus("II1111II", new Route("6k"))),
                busesSerials);
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
        given(driverRepository.findDriverByEmail("freddy.mercury@gmail.com"))
                .willReturn(new Driver("freddy.mercury@gmail.com", new Bus("EO3030EO", new Route("30")), true));
        given(busRepository.findBusBySerialNumber("BO1111RA")).willReturn(new Bus("BO1111RA", new Route("8R")));
        // WHEN
        adminService.assignDriverToBus("freddy.mercury@gmail.com", "BO1111RA");
        // THEN
        verify(driverRepository).updateDriverSetBus(
                new Driver("freddy.mercury@gmail.com", new Bus("EO3030EO", new Route("30")), true),
                new Bus("BO1111RA", new Route("8R")));
    }

    @Test
    void vacateDriverFromBus_shouldInitiateVacatingDriverFromBus() {
        // GIVEN
        given(driverRepository.findDriverByEmail("sponge.bob@square.pants"))
                .willReturn(new Driver("sponge.bob@square.pants", new Bus("OL0101OL", new Route("O1")), true));
        // WHEN
        adminService.vacateDriverFromBus("sponge.bob@square.pants");
        // THEN
        verify(driverRepository).updateDriverSetBus(
                new Driver("sponge.bob@square.pants", new Bus("OL0101OL", new Route("O1")), true),
                new Bus(null, null));
    }

    @Test
    void assignBusToRoute_shouldInitiateAssigningBusToRoute() {
        // GIVEN
        given(busRepository.findBusBySerialNumber("XO7040Z0")).willReturn(new Bus("XO7040Z0", new Route("666")));
        given(routeRepository.findRouteByName("69")).willReturn(new Route("69"));
        // WHEN
        adminService.assignBusToRoute("XO7040Z0", "69");
        // THEN
        verify(busRepository).updateBusSetRoute(new Bus("XO7040Z0", new Route("666")), new Route("69"));
    }

    @Test
    void getDriverInBus_shouldReturnDriverByBusSerialProvided() {
        // GIVEN
        given(busRepository.findBusBySerialNumber("AOL01AOL")).willReturn(new Bus("AOL01AOL", new Route("314")));
        given(driverRepository.findDriverByBus(new Bus("AOL01AOL", new Route("314"))))
                .willReturn(new Driver("ao@lo.cn", new Bus("AOL01AOL", new Route("314")), true));
        // WHEN
        Driver driver = adminService.getDriverInBus("AOL01AOL");
        // THEN
        assertEquals(new Driver("ao@lo.cn", new Bus("AOL01AOL", new Route("314")), true),
                driver);
    }
}
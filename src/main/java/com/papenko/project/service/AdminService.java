package com.papenko.project.service;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;

import java.util.List;

public class AdminService {
    private final DriverRepository driverRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    public AdminService(DriverRepository driverRepository, BusRepository busRepository, RouteRepository routeRepository) {
        this.driverRepository = driverRepository;
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
    }

    public List<Driver> getDrivers() {
        return driverRepository.findAllDrivers();
    }

    public List<Bus> getBuses() {
        return busRepository.findAllBuses();
    }

    public void assignDriverToBus(String driverEmail, String busSerial) {
        Driver driver = driverRepository.findDriverByEmail(driverEmail);
        Bus bus = busRepository.findBusBySerialNumber(busSerial);
        driverRepository.updateDriverSetBus(driver, bus);
    }

    public void vacateDriverFromBus(String driverEmail) {
        Driver driver = driverRepository.findDriverByEmail(driverEmail);
        driverRepository.updateDriverSetBus(driver, new Bus(null, null));
    }

    public List<Route> getRoutes() {
        return routeRepository.findAllRoutes();
    }

    public void assignBusToRoute(String busSerial, String routeName) {
        Bus bus = busRepository.findBusBySerialNumber(busSerial);
        Route route = routeRepository.findRouteByName(routeName);
        busRepository.updateBusSetRoute(bus, route);
    }

    public Driver getDriverInBus(String busSerial) {
        Bus bus = busRepository.findBusBySerialNumber(busSerial);
        return driverRepository.findDriverByBus(bus);
    }
}

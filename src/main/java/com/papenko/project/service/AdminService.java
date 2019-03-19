package com.papenko.project.service;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
    private final DriverRepository driverRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    public AdminService(DriverRepository driverRepository, BusRepository busRepository, RouteRepository routeRepository) {
        this.driverRepository = driverRepository;
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
    }

    public List<Driver> getDrivers() {
        LOGGER.debug("about to get drivers");
        List<Driver> allDrivers = driverRepository.findAllDrivers();
        LOGGER.debug("finished getting all drivers");
        return allDrivers;
    }

    public List<Bus> getBuses() {
        LOGGER.debug("about to get buses");
        List<Bus> allBuses = busRepository.findAllBuses();
        LOGGER.debug("finished getting all buses");
        return allBuses;
    }

    public void assignDriverToBus(String driverEmail, String busSerial) {
        LOGGER.debug("about to assign driver to a bus");
        Driver driver = driverRepository.findDriverByEmail(driverEmail);
        Bus bus = busRepository.findBusBySerialNumber(busSerial);
        driverRepository.updateDriverSetBus(driver, bus);
        LOGGER.debug("finished assigning driver to a bus");
    }

    public void vacateDriverFromBus(String driverEmail) {
        LOGGER.debug("about to vacate driver from a bus");
        Driver driver = driverRepository.findDriverByEmail(driverEmail);
        driverRepository.updateDriverSetBus(driver, new Bus(null, null));
        LOGGER.debug("finished vacating driver from a bus");
    }

    public List<Route> getRoutes() {
        LOGGER.debug("about to get routes");
        List<Route> allRoutes = routeRepository.findAllRoutes();
        LOGGER.debug("finished getting all routes");
        return allRoutes;
    }

    public void assignBusToRoute(String busSerial, String routeName) {
        LOGGER.debug("about to assign bus to a route");
        Bus bus = busRepository.findBusBySerialNumber(busSerial);
        Route route = routeRepository.findRouteByName(routeName);
        busRepository.updateBusSetRoute(bus, route);
        LOGGER.debug("finished assigning bus to a route");
    }

    public Driver getDriverInBus(String busSerial) {
        LOGGER.debug("about to get driver in a bus");
        Bus bus = busRepository.findBusBySerialNumber(busSerial);
        Driver driverByBus = driverRepository.findDriverByBus(bus);
        LOGGER.debug("finished getting driver in a bus");
        return driverByBus;
    }

    public void addRoute(String routeName) {
        LOGGER.debug("about to create a route");
        routeRepository.createRoute(routeName);
        LOGGER.debug("finished creating a route");
    }
}

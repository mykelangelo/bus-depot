package com.papenko.project.service;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.entity.User;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
import com.papenko.project.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AdminService {
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    public AdminService(UserRepository userRepository, DriverRepository driverRepository, BusRepository busRepository, RouteRepository routeRepository) {
        this.userRepository = userRepository;
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
        driverRepository.updateDriver(new Driver(driverEmail, busSerial));
    }

    public void vacateDriverFromBus(String driverEmail) {
        driverRepository.updateDriver(new Driver(driverEmail, null));
    }

    public List<Route> getRoutes() {
        return routeRepository.findAllRoutes();
    }

    public void assignBusToRoute(String busSerial, String routeName) {
        busRepository.updateBus(new Bus(busSerial, routeName));
    }
}

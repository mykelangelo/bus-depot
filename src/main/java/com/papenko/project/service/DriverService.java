package com.papenko.project.service;

import com.papenko.project.entity.Driver;
import com.papenko.project.repository.DriverRepository;

public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver findDriverByEmail(String driverEmail) {
        return driverRepository.findDriverByEmail(driverEmail);
    }
}
